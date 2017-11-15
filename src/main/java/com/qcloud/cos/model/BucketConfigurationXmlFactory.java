package com.qcloud.cos.model;

import java.util.List;
import java.util.Map;

import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.internal.XmlWriter;
import com.qcloud.cos.model.BucketLifecycleConfiguration.NoncurrentVersionTransition;
import com.qcloud.cos.model.BucketLifecycleConfiguration.Rule;
import com.qcloud.cos.model.BucketLifecycleConfiguration.Transition;
import com.qcloud.cos.model.CORSRule.AllowedMethods;
import com.qcloud.cos.model.Tag.LifecycleTagPredicate;
import com.qcloud.cos.model.Tag.Tag;
import com.qcloud.cos.model.lifecycle.LifecycleAndOperator;
import com.qcloud.cos.model.lifecycle.LifecycleFilter;
import com.qcloud.cos.model.lifecycle.LifecycleFilterPredicate;
import com.qcloud.cos.model.lifecycle.LifecyclePredicateVisitor;
import com.qcloud.cos.model.lifecycle.LifecyclePrefixPredicate;
import com.qcloud.cos.utils.DateUtils;


/**
 * Converts bucket configuration objects into XML byte arrays.
 */
public class BucketConfigurationXmlFactory {

    /**
     * Converts the specified {@link BucketCrossOriginConfiguration} object to an XML fragment that
     * can be sent to Qcloud COS.
     *
     * @param config The {@link BucketCrossOriginConfiguration}
     */
    /*
     * <CORSConfiguration>
             <CORSRule>
               <AllowedOrigin>http://www.foobar.com</AllowedOrigin>
               <AllowedMethod>GET</AllowedMethod>
               <MaxAgeSeconds>3000</MaxAgeSec>
               <ExposeHeader>x-cos-meta</ExposeHeader>
             </CORSRule>
       </CORSConfiguration>
     */
    public byte[] convertToXmlByteArray(BucketCrossOriginConfiguration config)
            throws CosClientException {

        XmlWriter xml = new XmlWriter();
        xml.start("CORSConfiguration");

        for (CORSRule rule : config.getRules()) {
            writeRule(xml, rule);
        }

        xml.end();

        return xml.getBytes();
    }

    /**
     * Converts the specified versioning configuration into an XML byte array.
     *
     * @param versioningConfiguration The configuration to convert.
     *
     * @return The XML byte array representation.
     */
    public byte[] convertToXmlByteArray(BucketVersioningConfiguration versioningConfiguration) {
        XmlWriter xml = new XmlWriter();
        xml.start("VersioningConfiguration");
        xml.start("Status").value(versioningConfiguration.getStatus()).end();

        xml.end();

        return xml.getBytes();
    }

    public byte[] convertToXmlByteArray(BucketLifecycleConfiguration config)
            throws CosClientException {

        XmlWriter xml = new XmlWriter();
        xml.start("LifecycleConfiguration");

        for (Rule rule : config.getRules()) {
            writeRule(xml, rule);
        }

        xml.end();

        return xml.getBytes();
    }

    private void writeRule(XmlWriter xml, CORSRule rule) {
        xml.start("CORSRule");
        if (rule.getId() != null) {
            xml.start("ID").value(rule.getId()).end();
        }
        if (rule.getAllowedOrigins() != null) {
            for (String origin : rule.getAllowedOrigins()) {
                xml.start("AllowedOrigin").value(origin).end();
            }
        }
        if (rule.getAllowedMethods() != null) {
            for (AllowedMethods method : rule.getAllowedMethods()) {
                xml.start("AllowedMethod").value(method.toString()).end();
            }
        }
        if (rule.getMaxAgeSeconds() != 0) {
            xml.start("MaxAgeSeconds").value(Integer.toString(rule.getMaxAgeSeconds())).end();
        }
        if (rule.getExposedHeaders() != null) {
            for (String header : rule.getExposedHeaders()) {
                xml.start("ExposeHeader").value(header).end();
            }
        }
        if (rule.getAllowedHeaders() != null) {
            for (String header : rule.getAllowedHeaders()) {
                xml.start("AllowedHeader").value(header).end();
            }
        }
        xml.end();// </CORSRule>
    }

    private void writeRule(XmlWriter xml, Rule rule) {
        xml.start("Rule");
        if (rule.getId() != null) {
            xml.start("ID").value(rule.getId()).end();
        }
        xml.start("Status").value(rule.getStatus()).end();
        writeLifecycleFilter(xml, rule.getFilter());

        addTransitions(xml, rule.getTransitions());
        addNoncurrentTransitions(xml, rule.getNoncurrentVersionTransitions());

        if (hasCurrentExpirationPolicy(rule)) {
            // The rule attributes below are mutually exclusive, the service will throw an error if
            // more than one is provided
            xml.start("Expiration");
            if (rule.getExpirationInDays() != -1) {
                xml.start("Days").value("" + rule.getExpirationInDays()).end();
            }
            if (rule.getExpirationDate() != null) {
                xml.start("Date").value(DateUtils.formatISO8601Date(rule.getExpirationDate()))
                        .end();
            }
            if (rule.isExpiredObjectDeleteMarker() == true) {
                xml.start("ExpiredObjectDeleteMarker").value("true").end();
            }
            xml.end(); // </Expiration>
        }

        if (rule.getNoncurrentVersionExpirationInDays() != -1) {
            xml.start("NoncurrentVersionExpiration");
            xml.start("NoncurrentDays")
                    .value(Integer.toString(rule.getNoncurrentVersionExpirationInDays())).end();
            xml.end(); // </NoncurrentVersionExpiration>
        }

        if (rule.getAbortIncompleteMultipartUpload() != null) {
            xml.start("AbortIncompleteMultipartUpload");
            xml.start("DaysAfterInitiation")
                    .value(Integer.toString(
                            rule.getAbortIncompleteMultipartUpload().getDaysAfterInitiation()))
                    .end();
            xml.end(); // </AbortIncompleteMultipartUpload>
        }

        xml.end(); // </Rule>
    }


    private void addTransitions(XmlWriter xml, List<Transition> transitions) {
        if (transitions == null || transitions.isEmpty()) {
            return;
        }

        for (Transition t : transitions) {
            if (t != null) {
                xml.start("Transition");
                if (t.getDate() != null) {
                    xml.start("Date");
                    xml.value(DateUtils.formatISO8601Date(t.getDate()));
                    xml.end();
                }
                if (t.getDays() != -1) {
                    xml.start("Days");
                    xml.value(Integer.toString(t.getDays()));
                    xml.end();
                }

                xml.start("StorageClass");
                xml.value(t.getStorageClass().toString());
                xml.end(); // <StorageClass>
                xml.end(); // </Transition>
            }
        }
    }

    private void addNoncurrentTransitions(XmlWriter xml,
            List<NoncurrentVersionTransition> transitions) {
        if (transitions == null || transitions.isEmpty()) {
            return;
        }

        for (NoncurrentVersionTransition t : transitions) {
            if (t != null) {
                xml.start("NoncurrentVersionTransition");
                if (t.getDays() != -1) {
                    xml.start("NoncurrentDays");
                    xml.value(Integer.toString(t.getDays()));
                    xml.end();
                }

                xml.start("StorageClass");
                xml.value(t.getStorageClassAsString());
                xml.end(); // </StorageClass>
                xml.end(); // </NoncurrentVersionTransition>
            }
        }
    }

    private void writeLifecycleFilter(XmlWriter xml, LifecycleFilter filter) {
        if (filter == null) {
            return;
        }

        xml.start("Filter");
        writeLifecycleFilterPredicate(xml, filter.getPredicate());
        xml.end();
    }

    private void writeLifecycleFilterPredicate(XmlWriter xml, LifecycleFilterPredicate predicate) {
        if (predicate == null) {
            return;
        }
        predicate.accept(new LifecyclePredicateVisitorImpl(xml));
    }

    private class LifecyclePredicateVisitorImpl implements LifecyclePredicateVisitor {
        private final XmlWriter xml;

        public LifecyclePredicateVisitorImpl(XmlWriter xml) {
            this.xml = xml;
        }

        @Override
        public void visit(LifecyclePrefixPredicate lifecyclePrefixPredicate) {
            writePrefix(xml, lifecyclePrefixPredicate.getPrefix());
        }

        @Override
        public void visit(LifecycleTagPredicate lifecycleTagPredicate) {
            writeTag(xml, lifecycleTagPredicate.getTag());
        }

        @Override
        public void visit(LifecycleAndOperator lifecycleAndOperator) {
            xml.start("And");
            for (LifecycleFilterPredicate predicate : lifecycleAndOperator.getOperands()) {
                predicate.accept(this);
            }
            xml.end(); // </And>
        }
    }

    public byte[] convertToXmlByteArray(BucketReplicationConfiguration replicationConfiguration) {
        XmlWriter xml = new XmlWriter();
        xml.start("ReplicationConfiguration");
        Map<String, ReplicationRule> rules = replicationConfiguration.getRules();

        final String role = replicationConfiguration.getRoleName();
        xml.start("Role").value(role).end();
        for (Map.Entry<String, ReplicationRule> entry : rules.entrySet()) {
            final String ruleId = entry.getKey();
            final ReplicationRule rule = entry.getValue();

            xml.start("Rule");
            xml.start("ID").value(ruleId).end();
            xml.start("Prefix").value(rule.getPrefix()).end();
            xml.start("Status").value(rule.getStatus()).end();

            final ReplicationDestinationConfig config = rule.getDestinationConfig();
            xml.start("Destination");
            xml.start("Bucket").value(config.getBucketQCS()).end();
            if (config.getStorageClass() != null) {
                xml.start("StorageClass").value(config.getStorageClass()).end();
            }
            xml.end();

            xml.end();
        }
        xml.end();
        return xml.getBytes();
    }

    /**
     * @param rule
     * @return True if rule has a current expiration (<Expiration/>) policy set
     */
    private boolean hasCurrentExpirationPolicy(Rule rule) {
        return rule.getExpirationInDays() != -1 || rule.getExpirationDate() != null
                || rule.isExpiredObjectDeleteMarker();
    }

    private void addParameterIfNotNull(XmlWriter xml, String xmlTagName, String value) {
        if (value != null) {
            xml.start(xmlTagName).value(value).end();
        }
    }

    private void writePrefix(XmlWriter xml, String prefix) {
        addParameterIfNotNull(xml, "Prefix", prefix);
    }

    private void writeTag(XmlWriter xml, Tag tag) {
        if (tag == null) {
            return;
        }
        xml.start("Tag");
        xml.start("Key").value(tag.getKey()).end();
        xml.start("Value").value(tag.getValue()).end();
        xml.end();
    }

}
