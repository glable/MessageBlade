sourceList
===
select ACCOUNT,MOBILE,CONTENT,CREATE_TIME,REPORT,REPORT_TIME,MONTH_SEQ from MT_MSG

list
===
select * from MT_MSG

mo
===
select ACCOUNT,MOBILE,CONTENT,CREATE_TIME from MO_MSG

report
===
select ACCOUNT,TO_CHAR(REPORT_DATE, 'YYYY-MM-DD') REPORT_DATE, trunc(success_num/send_num*100, 2) ||'%' SUCCESS from ACCOUNT_DAY_REPORT where report_date > sysdate -1 and success_num/send_num < 0.8 order by report_date desc
audit
===
select * from AUDIT_SMS