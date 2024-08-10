package capstone.dessert.mococobackend.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class KakaoSearchResponse {

    @JsonProperty("meta")
    private Meta meta;

    @JsonProperty("documents")
    private List<Document> documents;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Getter
    @Setter
    public static class Meta {

        @JsonProperty("is_end")
        private boolean isEnd;

        @JsonProperty("pageable_count")
        private int pageableCount;

        @JsonProperty("total_count")
        private int totalCount;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Getter
    @Setter
    public static class Document {

        @JsonProperty("address")
        private Address address;

        @JsonProperty("address_name")
        private String addressName;

        @JsonProperty("address_type")
        private String addressType;

        @JsonProperty("road_address")
        private String roadAddress;

        @JsonProperty("x")
        private double x;

        @JsonProperty("y")
        private double y;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Getter
    @Setter
    public static class Address {

        @JsonProperty("address_name")
        private String addressName;

        @JsonProperty("b_code")
        private String bCode;

        @JsonProperty("h_code")
        private String hCode;

        @JsonProperty("main_address_no")
        private String mainAddressNo;

        @JsonProperty("mountain_yn")
        private String mountainYn;

        @JsonProperty("region_1depth_name")
        private String region1depthName;

        @JsonProperty("region_2depth_name")
        private String region2depthName;

        @JsonProperty("region_3depth_h_name")
        private String region3depthHName;

        @JsonProperty("region_3depth_name")
        private String region3depthName;

        @JsonProperty("sub_address_no")
        private String subAddressNo;

        @JsonProperty("x")
        private double x;

        @JsonProperty("y")
        private double y;
    }
}
