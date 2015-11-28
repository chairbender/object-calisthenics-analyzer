## object-calisthenics-analyzer

A tool for analyzing Java code for its adherence to [Object Calisthenics](http://www.cs.helsinki.fi/u/luontola/tdd-2009/ext/ObjectCalisthenics.pdf).

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
