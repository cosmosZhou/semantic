package com.patsnap.core.analysis.constants;

import java.util.HashSet;
import java.util.Set;

/**
 * Patent data constant
 *
 * @author xiaweibing on 2019/10/29
 */
public class PatentDataConstant {

    /**
     * version of patent api to invoke
     */
    public static final String PATENT_DATA_API_VERSION = "2.5";

    public static Set<String> BASE_FIELD_SET = new HashSet<>(8);

    public static final String CLASSIFICATION_GROUP_POSTFIX = "/00";

    static {
        BASE_FIELD_SET.add(Fields.TITLE);
        BASE_FIELD_SET.add(Fields.PATENT_ID);
        BASE_FIELD_SET.add(Fields.PN);
        BASE_FIELD_SET.add(Fields.ANCS);
        BASE_FIELD_SET.add(Fields.PBD);
    }

    /**
     * field constants
     */
    public interface Fields {
        String INPADOC_FAMILY_COUNT = "INPADOC_FAMILY_COUNT";
        String EXTEND_FAMILY_COUNT = "EXTEND_FAMILY_COUNT";
        String SIMPLE_FAMILY_COUNT = "SIMPLE_FAMILY_COUNT";
        String CLAIM_COUNT = "CLAIM_COUNT";
        String CITED_COUNT = "CITED_COUNT";

//        String TITLE = "TITLE";
        String TITLE = "TTL";
        String TITLE_LANG = "TITLE_LANG";
        String TITLE_TRAN = "TITLE_TRAN";
        String TITLE_TRAN_LANG = "TITLE_TRAN_LANG";

        String PATENT_ID = "PATENT_ID";
        String PN = "PN";
        String ANCS = "ANCS";
        String PBD = "PBD";
        String ABST = "ABST";
        String ABST_TRAN = "ABST_TRAN";
        String ABST_TRAN_LANG = "ABST_TRAN_LANG";
        String ABST_LANG = "ABST_LANG";

        // patent valuation related
        String PV = "PV";
        String SCORE_A = "SCORE_A";  // 申请(专利权)人得分
        String SCORE_EA = "SCORE_EA"; // 市场吸引力得分
        String SCORE_EC = "SCORE_EC"; // 市场覆盖得分
        String SCORE_L = "SCORE_L";   // 法律得分
        String SCORE_T = "SCORE_T"; // 技术得分

        // classification codes
        String IPCR = "IPCR";

        // patent date fields
        String APD = "APD";
        String EXDT = "EXDT";
        String PRIORITY_DATE = "PRIORITY_DATE";

        String SIMPLE_LEGAL_STATUS = "SIMPLE_LEGAL_STATUS";
        String FAMILY_ID = "FAMILY_ID";
    }
}
