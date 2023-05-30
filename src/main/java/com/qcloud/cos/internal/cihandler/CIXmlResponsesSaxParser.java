/*
 * Copyright 2010-2019 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.

 * According to cos feature, we modify some class，comment, field name, etc.
 */


package com.qcloud.cos.internal.cihandler;

import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.internal.XmlResponsesSaxParser;
import com.qcloud.cos.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class CIXmlResponsesSaxParser {

    private static final Logger log = LoggerFactory.getLogger(XmlResponsesSaxParser.class);

    private XMLReader xr = null;

    public CIXmlResponsesSaxParser() {
        try {
            xr = XMLReaderFactory.createXMLReader();
        } catch (SAXException e) {
            throw new CosClientException("Couldn't initialize a SAX driver to create an XMLReader", e);
        }
    }

    public void parseXmlInputStream(DefaultHandler handler, InputStream inputStream) throws IOException {
        try {

            if (log.isDebugEnabled()) {
                log.debug("Parsing XML response document with handler: " + handler.getClass());
            }

            BufferedReader breader = new BufferedReader(new InputStreamReader(inputStream, StringUtils.UTF8));
            CharacterFilter characterFilter = new CharacterFilter(breader);
            xr.setContentHandler(handler);
            xr.setErrorHandler(handler);
            xr.parse(new InputSource(characterFilter));

        } catch (IOException e) {
            throw e;

        } catch (Throwable t) {
            try {
                inputStream.close();
            } catch (IOException e) {
                if (log.isErrorEnabled()) {
                    log.error("Unable to close response InputStream up after XML parse failure", e);
                }
            }
            throw new CosClientException("Failed to parse XML document with handler " + handler.getClass(), t);
        }
    }

}

