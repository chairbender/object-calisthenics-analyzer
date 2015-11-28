## object-calisthenics-analyzer

A tool for analyzing Java code for its adherence to [Object Calisthenics](http://www.cs.helsinki.fi/u/luontola/tdd-2009/ext/ObjectCalisthenics.pdf).

Except for Rule 6. Checking for abbreviations is not a simple task. Rule 6 also recommends keeping method and class names
to 2 or fewer words. I think it's beneficial to have descriptive method, class, and variable names. Because of that,
I think a limit of 2 words would be, at best, a waste of time due to lots of false positives; and, at worst, a 
discouragement from creating adequately descriptive names.