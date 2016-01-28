package jp.toneko.decidedartsteams;

/**
 * Created by yachi-shunji on 16/01/12.
 */
public enum FragmentTags {
    INPUT(0, "fragment_input"),
    RESULT(1, "fragment_result");

    private Integer value;
    private String name;

    /**
     * コンストラクタ
     * @param value value
     * @param name name
     */
    private FragmentTags(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    /**
     * ID
     * @return ID
     */
    public Integer getValue() {
        return value;
    }

    /**
     * 名称設定
     * @return 名称
     */
    public String getName() {
        return name;
    }

    /**
     * 取得
     * @param value ID
     * @return 文字列
     */
    public static FragmentTags getEnum(Integer value) {
        for (FragmentTags status : FragmentTags.values()) {
            if (status.value.equals(value)) {
                return status;
            }
        }
        return INPUT;
    }
}

