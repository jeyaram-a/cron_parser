package org.example.cron;

// Tests taken from https://docs.oracle.com/cd/E12058_01/doc/doc.1014/e12030/cron_expressions.htm
public class RandomTests {

//    @Test
    void test1() {
        var cron = new CronExpression("0 12 * * * gg");
        cron.prettyPrint();

        cron = new CronExpression("15 10 ? * * gg");
        cron.prettyPrint();

        cron = new CronExpression("15 10 ? * WED gg");
        cron.prettyPrint();

        cron = new CronExpression("* 14 * * ? gg");
        cron.prettyPrint();

        cron = new CronExpression("11 11 11 11 ? gg");
        cron.prettyPrint();

        cron = new CronExpression("0 12 1/5 * ? gg");
        cron.prettyPrint();

        cron = new CronExpression("15 10 15 * ? gg");
        cron.prettyPrint();

        cron = new CronExpression("15 10 ? * MON-FRI gg");
        cron.prettyPrint();

        cron = new CronExpression("10,44 14 ? 3 WED gg");
        cron.prettyPrint();


//        cron = new CronExpression("? 14 ? 3 WED gg");
//        cron.prettyPrint();

    }
}
