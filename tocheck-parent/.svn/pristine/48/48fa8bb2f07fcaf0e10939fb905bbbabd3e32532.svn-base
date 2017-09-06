package com.tocheck.parent.common.ssdb;

public class Sample {
    private static SSDB ssdb;
    private static final String host = "60.205.113.26";
    private static final int port = 8888;

    static {
        try {
            ssdb = new SSDB(host, port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void resetSkipList(long docCheckId, boolean refer, boolean segment) throws Exception {
        if (refer) {
            String key = "refer" + docCheckId;
            ssdb.del(key);
        }
        if (segment) {
            String key = "segment" + docCheckId;
            ssdb.del(key);
        }
    }

    public static void main(String[] args) throws Exception {
        String docCheckId = "checkReport1";
		String content = "同时针对网络违法行为具有易实施、成本低廉、隐蔽性强和危害性广等特点,在强化立法和执法的同时,尝试建立一套如网上法院、网上仲裁、网络公证等法律服务与保障体系,以更加方便和快捷的方式防止和打击电子商务领域的非法经营和网络违法违规行为。";
		ssdb.set(docCheckId, content);
		String value = new String(ssdb.get(docCheckId),"UTF-8");
//		String value = new String(ssdb.get("13665279309content"));
		System.out.println(value);
		System.out.println(content.endsWith(value));
//        BigDecimal size = BigDecimal.valueOf(2048 * 1024 * 1024 * 1d);
//        BigDecimal size = BigDecimal.valueOf(2048d);
//        System.out.println("1==>" + size.setScale(2));
//        BigDecimal unit = BigDecimal.valueOf(1204d);
//        size = size.multiply(unit);
//        size = size.multiply(unit);
//        System.out.println("2==>" + size.setScale(2));
//        BigDecimal size1 = BigDecimal.valueOf(2048d);
//        size1 = size1.multiply(unit).multiply(unit);
//        System.out.println("2==>" + size1.setScale(2));
//        size = size.divide(unit);
//        size = size.divide(unit);
//        System.out.println("3==>" + size.setScale(2));
//        Date date = new Date("Jul 25, 2016 2:06:17 PM");
//        System.out.println(date.getTime());
//        resetSkipList(20l,true,true);
//		System.out.printf(new String(ssdb.get("server1583637881697"),"UTF-8"));
//		ssdb.del("server1557914629722");
//		System.out.printf(new String(ssdb.get("wechat_token"),"UTF-8"));
//        ssdb.del("15111381871title");
//        byte[] title = ssdb.get("18336541844content");
//        if (null != title) {
//            String result = StringUtils.repeat(" ","");
//            System.out.println(new String(title));
//        } else {
//            System.out.println("title=null");
//        }
        for(int i = 1; i <= 3 ; i ++){
            ssdb.del("topicValue" + i);
        }
    }
}
