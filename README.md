Mambu XBRL Mix Market App
===================

The Mambu XBRL Mix Market App allow organizations to extract their financial and key indicators in the industry standard [XBRL](http://en.wikipedia.org/wiki/XBRL) format which can imported by [MixMarket](http://www.mixmarket.org/) or used in many industry-standard reporting tools. 

Certain indicators such as number of clients, number of credit officers and so forth are automatically populated along with the currency used by the organization and the date range of the report.

Installation
-----

To use the Mambu XBRL Mix Market App, organizations must have APIs and Apps enabled on their account and need to [install](http://developer.mambu.com/api-apps-overview/app-installation) the app using the following source location **https://dl.dropbox.com/u/6308256/Apps/mixmarket.xml**

Usage
-----

Fields can be populated using either standard GL Codes or specific numbers. For instance when defining the "Cash And Cash Equivalents" field, any of the following combinations are possible
- {52000} -> retrieve the balance from GL Account 52000
- {52000} - {52331} -> retrieve the balance from GL Account 52000 and subtract the balance of 52331
- 60000000 -> use the value of 60000000 (in local organization currency)
- {52000} - 5000 -> retrieve the balance from GL Account 52000 and subtract 5000

Any mathematical expression can be used to add, subtract, multiply or divide the input where the value is not retreived from just a single GL Account.

When submitting a report to MixMarket, organizations will need to change their organization identifier in the xml file to their unique MixMarket identifier.