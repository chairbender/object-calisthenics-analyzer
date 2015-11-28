## object-calisthenics-analyzer

A tool for analyzing Java code for its adherence to [Object Calisthenics](http://www.cs.helsinki.fi/u/luontola/tdd-2009/ext/ObjectCalisthenics.pdf).

## Usage
````
//get all the violations
ViolationMonitor violationMonitor = 
  ObjectCalisthenicsAnalyzer.analyze(new File("fileOrProjectFolderToAnalyze"),"UTF-8");
List<Violation> violations = violations.getAllViolations();

//print info about the specific violation (the rule and the suggestion for fixing it)
Violation aViolation = violations.get(0);
System.out.println(aViolation.getRuleInfo().describe());

//gets the specific node in the source code where the violation occurred.
//From this, you can print the line number of the violation and get all sorts of
//other info about the location within the source code.
Node violationLocation = aViolation.getViolationLocation()
````

## Stuff It Won't Find For You
For various reasons, there's a few things mentioned in Object Calisthenics that this program doesn't check for.

### Rule 6 - Don't Abbreviate
Checking for abbreviations is not a simple task if you care about false positives. Rule 6 also recommends keeping method and class names
to 2 or fewer words. I think it's beneficial to have descriptive method, class, and variable names. Because of that,
I think a limit of 2 words would be, at best, a waste of time due to lots of false positives; and, at worst, a 
discouragement from creating adequately descriptive names.

### Rule 7 - Packages should have no more than 10 classes
(Note that we do still check that your classes are under 50 lines of code) 

Currently, this program is designed so it can be run on individual Java source files in isolation. Eventually, 
I could add support for parsing an entire Java project as an option. For now, check this one yourself.

### Rule 9 - No getters/setters/properties
It will detect some obvious getters / setters. However, it will still miss cases where a method is, effectively, a getter
or setter, but does something other than simply setting or retrieving the value of a class member/field. For example,
if I have an Employee who contains a PayrollInfo member and I have a method on Employee called getAnnualSalary which
returns payrollInfo.getAnnualSalary(), effectively, that method is acting as a getter and violating the spirit
of this rule, but the analyzer will miss it. It WOULD catch a method like Employee.getPayrollInfo() which simply returns
the payrollInfo member.

So, figure out if a method is acting as a getter / setter, and try to design your code so you can TELL a class to
do something instead of ASKing it for some data. Tell Employee to be paid (employee.pay()) instead of doing getSalary()
and getBonus() and then calling BankAccount.setValue() with the value of that calculation. Tell, don't ask.
