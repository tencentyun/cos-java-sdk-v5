package com.qcloud.cos.model.ciModel.metaInsight;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.qcloud.cos.internal.CIServiceRequest;

import java.util.List;

/**
 * 多模态混合检索请求
 * <p>
 * 对应 MetaInsight 接口：POST /datasetquery/hybridsearch。
 * 支持图片（pic）/ 文本（text）双模式检索，
 * 通过 {@code Templates} 字段控制返回 ImageSearch（图像级）或 DocSearch（文档级）结果。
 * </p>
 *
 * @since 5.6.271 (MetaInsight V2.7.0)
 * @see <a href="https://write.woa.com/document/200892020456558592">官方文档</a>
 */
public class DatasetHybridSearchRequest extends CIServiceRequest {

    /**
     * 数据集名称，同一个账户下唯一。
     * 是否必传：是
     */
    private String datasetName;

    /**
     * 检索模式：
     * <ul>
     *   <li>{@code pic}（默认）：以图搜图/搜文档，需配合 {@link #searchURIs}</li>
     *   <li>{@code text}：以文搜图/搜文档，需配合 {@link #searchText}</li>
     * </ul>
     * 是否必传：否
     */
    private String mode;

    /**
     * 检索模板，决定返回结果类型：
     * <ul>
     *   <li>{@code ImageSearch}：返回图像检索结果（{@code ImageResult[]}）</li>
     *   <li>{@code DocSearch}：返回文档检索结果（{@code DocResult[]}）</li>
     * </ul>
     * 是否必传：是
     */
    private String templates;

    /**
     * 待检索的图片 URI 列表，{@code Mode=pic} 时必传。
     * <p>
     * 字段名含连续大写字母「URIs」，Jackson UPPER_CAMEL_CASE 全局策略对该类字段的转换行为不稳定，
     * 因此显式指定 JSON 键为 {@code SearchURIs}（仅此字段单独处理，不影响全局策略）。
     * </p>
     * 是否必传：条件必传（Mode=pic 时）
     */
    @JsonProperty("SearchURIs")
    private List<String> searchURIs;

    /**
     * 待检索的文本内容，{@code Mode=text} 时必传，长度 ≤ 60 UTF-8 字符。
     * 是否必传：条件必传（Mode=text 时）
     */
    private String searchText;

    /**
     * 返回结果数量上限，默认 10，取值范围 (0, 100]。
     * 是否必传：否
     */
    private Integer limit;

    /**
     * 相关度匹配阈值。出参 {@code Score} 中只有超过该阈值的结果才会返回。
     * 范围：0-100，默认 0，推荐值 80。
     * 是否必传：否
     */
    private Integer matchThreshold;

    /**
     * 过滤表达式，嵌套 JSON 字符串字面量直传到后端，例如：
     * <pre>{@code {"$and":[{"ContentType":{"$eq":"application/pdf"}},{"Size":{"$gt":1024}}]}}</pre>
     * 文档定义为 Container（JSON 对象），此处通过 {@link JsonRawValue} 将字符串按原始 JSON
     * 序列化为对象，避免被转义成字符串字面量。SDK 不解析此字段，调用方需自行保证 JSON 合法。
     * 支持的标量字段与操作符见官方文档《标量过滤字段与操作符支持列表》。
     * 是否必传：否
     */
    @JsonRawValue
    private String filter;

    public String getDatasetName() { return datasetName; }

    public void setDatasetName(String datasetName) { this.datasetName = datasetName; }

    public String getMode() { return mode; }

    public void setMode(String mode) { this.mode = mode; }

    public String getTemplates() { return templates; }

    public void setTemplates(String templates) { this.templates = templates; }

    public List<String> getSearchURIs() { return searchURIs; }

    public void setSearchURIs(List<String> searchURIs) { this.searchURIs = searchURIs; }

    public String getSearchText() { return searchText; }

    public void setSearchText(String searchText) { this.searchText = searchText; }

    public Integer getLimit() { return limit; }

    public void setLimit(Integer limit) { this.limit = limit; }

    public Integer getMatchThreshold() { return matchThreshold; }

    public void setMatchThreshold(Integer matchThreshold) { this.matchThreshold = matchThreshold; }

    public String getFilter() { return filter; }

    public void setFilter(String filter) { this.filter = filter; }

}
