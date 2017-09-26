package io.spring.wso2.model.dto;

import java.util.Objects;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Tier
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2017-09-08T20:14:51.431Z")
public class Tier {
	@SerializedName("name")
	private String name = null;

	@SerializedName("description")
	private String description = null;

	/**
	 * Gets or Sets tierLevel
	 */
	@JsonAdapter(TierLevelEnum.Adapter.class)
	public enum TierLevelEnum {
		API("API"),

		APPLICATION("APPLICATION"),

		RESOURCE("RESOURCE");

		private String value;

		TierLevelEnum(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		@Override
		public String toString() {
			return String.valueOf(value);
		}

		public static TierLevelEnum fromValue(String text) {
			for (TierLevelEnum b : TierLevelEnum.values()) {
				if (String.valueOf(b.value).equals(text)) {
					return b;
				}
			}
			return null;
		}

		public static class Adapter extends TypeAdapter<TierLevelEnum> {
			@Override
			public void write(final JsonWriter jsonWriter, final TierLevelEnum enumeration) throws IOException {
				jsonWriter.value(enumeration.getValue());
			}

			@Override
			public TierLevelEnum read(final JsonReader jsonReader) throws IOException {
				String value = jsonReader.nextString();
				return TierLevelEnum.fromValue(String.valueOf(value));
			}
		}
	}

	@SerializedName("tierLevel")
	private TierLevelEnum tierLevel = null;

	@SerializedName("attributes")
	private Map<String, String> attributes = null;

	@SerializedName("requestCount")
	private Long requestCount = null;

	@SerializedName("unitTime")
	private Long unitTime = null;

	@SerializedName("timeUnit")
	private String timeUnit = null;

	/**
	 * This attribute declares whether this tier is available under commercial
	 * or free
	 */
	@JsonAdapter(TierPlanEnum.Adapter.class)
	public enum TierPlanEnum {
		FREE("FREE"),

		COMMERCIAL("COMMERCIAL");

		private String value;

		TierPlanEnum(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		@Override
		public String toString() {
			return String.valueOf(value);
		}

		public static TierPlanEnum fromValue(String text) {
			for (TierPlanEnum b : TierPlanEnum.values()) {
				if (String.valueOf(b.value).equals(text)) {
					return b;
				}
			}
			return null;
		}

		public static class Adapter extends TypeAdapter<TierPlanEnum> {
			@Override
			public void write(final JsonWriter jsonWriter, final TierPlanEnum enumeration) throws IOException {
				jsonWriter.value(enumeration.getValue());
			}

			@Override
			public TierPlanEnum read(final JsonReader jsonReader) throws IOException {
				String value = jsonReader.nextString();
				return TierPlanEnum.fromValue(String.valueOf(value));
			}
		}
	}

	@SerializedName("tierPlan")
	private TierPlanEnum tierPlan = null;

	@SerializedName("stopOnQuotaReach")
	private Boolean stopOnQuotaReach = null;

	public Tier name(String name) {
		this.name = name;
		return this;
	}

	/**
	 * Get name
	 * 
	 * @return name
	 **/
	@ApiModelProperty(example = "Platinum", required = true, value = "")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Tier description(String description) {
		this.description = description;
		return this;
	}

	/**
	 * Get description
	 * 
	 * @return description
	 **/
	@ApiModelProperty(example = "Allows 50 request(s) per minute.", value = "")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Tier tierLevel(TierLevelEnum tierLevel) {
		this.tierLevel = tierLevel;
		return this;
	}

	/**
	 * Get tierLevel
	 * 
	 * @return tierLevel
	 **/
	@ApiModelProperty(example = "api", value = "")
	public TierLevelEnum getTierLevel() {
		return tierLevel;
	}

	public void setTierLevel(TierLevelEnum tierLevel) {
		this.tierLevel = tierLevel;
	}

	public Tier attributes(Map<String, String> attributes) {
		this.attributes = attributes;
		return this;
	}

	public Tier putAttributesItem(String key, String attributesItem) {
		if (this.attributes == null) {
			this.attributes = new HashMap<String, String>();
		}
		this.attributes.put(key, attributesItem);
		return this;
	}

	/**
	 * Custom attributes added to the tier policy
	 * 
	 * @return attributes
	 **/
	@ApiModelProperty(example = "{}", value = "Custom attributes added to the tier policy ")
	public Map<String, String> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}

	public Tier requestCount(Long requestCount) {
		this.requestCount = requestCount;
		return this;
	}

	/**
	 * Maximum number of requests which can be sent within a provided unit time
	 * 
	 * @return requestCount
	 **/
	@ApiModelProperty(example = "50", required = true, value = "Maximum number of requests which can be sent within a provided unit time ")
	public Long getRequestCount() {
		return requestCount;
	}

	public void setRequestCount(Long requestCount) {
		this.requestCount = requestCount;
	}

	public Tier unitTime(Long unitTime) {
		this.unitTime = unitTime;
		return this;
	}

	/**
	 * Get unitTime
	 * 
	 * @return unitTime
	 **/
	@ApiModelProperty(example = "60000", required = true, value = "")
	public Long getUnitTime() {
		return unitTime;
	}

	public void setUnitTime(Long unitTime) {
		this.unitTime = unitTime;
	}

	public Tier timeUnit(String timeUnit) {
		this.timeUnit = timeUnit;
		return this;
	}

	/**
	 * Get timeUnit
	 * 
	 * @return timeUnit
	 **/
	@ApiModelProperty(example = "min", value = "")
	public String getTimeUnit() {
		return timeUnit;
	}

	public void setTimeUnit(String timeUnit) {
		this.timeUnit = timeUnit;
	}

	public Tier tierPlan(TierPlanEnum tierPlan) {
		this.tierPlan = tierPlan;
		return this;
	}

	/**
	 * This attribute declares whether this tier is available under commercial
	 * or free
	 * 
	 * @return tierPlan
	 **/
	@ApiModelProperty(example = "FREE", required = true, value = "This attribute declares whether this tier is available under commercial or free ")
	public TierPlanEnum getTierPlan() {
		return tierPlan;
	}

	public void setTierPlan(TierPlanEnum tierPlan) {
		this.tierPlan = tierPlan;
	}

	public Tier stopOnQuotaReach(Boolean stopOnQuotaReach) {
		this.stopOnQuotaReach = stopOnQuotaReach;
		return this;
	}

	/**
	 * By making this attribute to false, you are capabale of sending requests
	 * even if the request count exceeded within a unit time
	 * 
	 * @return stopOnQuotaReach
	 **/
	@ApiModelProperty(example = "true", required = true, value = "By making this attribute to false, you are capabale of sending requests even if the request count exceeded within a unit time ")
	public Boolean getStopOnQuotaReach() {
		return stopOnQuotaReach;
	}

	public void setStopOnQuotaReach(Boolean stopOnQuotaReach) {
		this.stopOnQuotaReach = stopOnQuotaReach;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Tier tier = (Tier) o;
		return Objects.equals(this.name, tier.name) && Objects.equals(this.description, tier.description)
				&& Objects.equals(this.tierLevel, tier.tierLevel) && Objects.equals(this.attributes, tier.attributes)
				&& Objects.equals(this.requestCount, tier.requestCount) && Objects.equals(this.unitTime, tier.unitTime)
				&& Objects.equals(this.timeUnit, tier.timeUnit) && Objects.equals(this.tierPlan, tier.tierPlan)
				&& Objects.equals(this.stopOnQuotaReach, tier.stopOnQuotaReach);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, description, tierLevel, attributes, requestCount, unitTime, timeUnit, tierPlan,
				stopOnQuotaReach);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class Tier {\n");

		sb.append("    name: ").append(toIndentedString(name)).append("\n");
		sb.append("    description: ").append(toIndentedString(description)).append("\n");
		sb.append("    tierLevel: ").append(toIndentedString(tierLevel)).append("\n");
		sb.append("    attributes: ").append(toIndentedString(attributes)).append("\n");
		sb.append("    requestCount: ").append(toIndentedString(requestCount)).append("\n");
		sb.append("    unitTime: ").append(toIndentedString(unitTime)).append("\n");
		sb.append("    timeUnit: ").append(toIndentedString(timeUnit)).append("\n");
		sb.append("    tierPlan: ").append(toIndentedString(tierPlan)).append("\n");
		sb.append("    stopOnQuotaReach: ").append(toIndentedString(stopOnQuotaReach)).append("\n");
		sb.append("}");
		return sb.toString();
	}

	/**
	 * Convert the given object to string with each line indented by 4 spaces
	 * (except the first line).
	 */
	private String toIndentedString(Object o) {
		if (o == null) {
			return "null";
		}
		return o.toString().replace("\n", "\n    ");
	}

}
