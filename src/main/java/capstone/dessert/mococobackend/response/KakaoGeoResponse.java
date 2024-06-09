package capstone.dessert.mococobackend.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class KakaoGeoResponse {

    @JsonProperty("meta")
    private Meta meta;

    @JsonProperty("documents")
    private List<Document> documents;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Getter
    @Setter
    public static class Meta {
        @JsonProperty("total_count")
        private int totalCount;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Getter
    @Setter
    public static class Document {
        @JsonProperty("region_type")
        private String regionType;

        @JsonProperty("code")
        private String code;

        @JsonProperty("address_name")
        private String addressName;

        @JsonProperty("region_1depth_name")
        private String regionOneDepthName;

        @JsonProperty("region_2depth_name")
        private String regionTwoDepthName;

        @JsonProperty("region_3depth_name")
        private String regionThreeDepthName;

        @JsonProperty("region_4depth_name")
        private String regionFourDepthName;

        @JsonProperty("x")
        private String x;

        @JsonProperty("y")
        private String y;
    }
}
