Many german banks provide an export of a customers account statement in CSV format (e.g.http://www.vr.de/ or http://www.sparkasse.de/). Unfortunatly CSV is not importable by software as gnucash or kmymoney.

The project is a commandline tool which does the conversion and gives the user the opportunity to change imported data based on some pattern matching rules in an xml file.

The current state of the project is a maven project, which build an executable jar. The jar is able to convert the output used by the "Volksbank" http://www.vr.de/. Other banks can be converted using a beanshell snippet and configure them in an xml file.

SYNOPSIS:

```
/csv2qif$ java \
   -cp etc/:lib/log4j.jar:lib/csv2qif-1.0.jar \
       # /etc contains logging config
   -jar lib/csv2qif-1.0. \
   -c etc/transformer.xml \ # configuration
   -i source-transactions.csv \ # input data
   -n vb-sauerland \ # bank definition in xml file to be used
!Type:Bank
Lcategory:two
MTHANK MEMO TEXT
A123456789
D12.07.2010
PMRFOOBAR
T-50.0
^
Lcategory:one
MTHANK MEMO TEXT
A123456789
D09.07.2010
PMR FOOBAR
T-66.64
^
```