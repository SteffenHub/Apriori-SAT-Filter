# Apriori-SAT-Filter


This code is an improved version of the Apriori-algorithm
using a Sat-Solver.   
The Apriori-algorithm is an algorithm for association rule mining.  
To run the code and get an advantage of a Sat-Solver you
have to provide a ruleset and sales data.


# Sales
The sales file can be found in (data/input_data/sales/randomConfBuilder_result_10_freq_result_Phone_example_10Decimal.txt) and has been generated with the [Random-Configuration-Builder](https://github.com/SteffenHub/Random-Configuration-Builder).  
the input data for the Random-Configuration-Builder can be found in (data/input_data/installation_rates/freq_result_Phone_example_10Decimal.txt) and the rule file in the [Rule-Section](#rules).  

Each row shows a sale of a Phone. 1 means the equipment was chosen, 0 means the equipment was not chosen.

| Sales             | 64 Gb Storage | 128 Storage | 256 GB Storage | Black color | 8 GB RAM | 16 GB RAM |
|-------------------|---------------|-------------|----------------|-------------|----------|-----------|
| Phone 1           | 0             | 0           | 1              | 1           | 0        | 1         |
| Phone 2           | 0             | 0           | 1              | 1           | 1        | 0         |
| Phone 3           | 0             | 0           | 1              | 1           | 0        | 1         |
| Phone 4           | 0             | 0           | 1              | 1           | 0        | 1         |
| Phone 5           | 0             | 1           | 0              | 1           | 1        | 0         |
| Phone 6           | 1             | 0           | 0              | 1           | 1        | 0         |
| Phone 7           | 1             | 0           | 0              | 1           | 1        | 0         |
| Phone 8           | 1             | 0           | 0              | 1           | 1        | 0         |
| Phone 9           | 1             | 0           | 0              | 1           | 1        | 0         |
| Phone 10          | 0             | 0           | 1              | 1           | 1        | 0         |
| Installation rate | 40%           | 10%         | 50%            | 100%        | 70%      | 30%       |


# Result
These results are made with the following input data:
- The Sales from: [Sales-Section](#sales)
- The Rules form: [Rule-Section](#rules)
- minimum support of 40%
- minimum confidence of 40%

## With SAT:
The args to run this calculation:
> --rule-file data/input_data/rule_files/Phone_example.cnf   
> --order-file data/input_data/sales/randomConfBuilder_result_10_freq_result_Phone_example_10Decimal.txt   
> --minSupport 0.4  
> --minConfidence 0.4  
> --max-depth 200  
> --use-sat-solver true  
> --use-procedure MApriori

The result: 
> Only the relevant solutions
> 
> [1 -> 5] support: 0.4 confidence: 1.0  
> [5 -> 1] support: 0.4 confidence: 0.5714285714285715  


## Without SAT
The args to run this calculation:
> --rule-file data/input_data/rule_files/Phone_example.cnf   
> --order-file data/input_data/sales/randomConfBuilder_result_10_freq_result_Phone_example_10Decimal.txt   
> --minSupport 0.4  
> --minConfidence 0.4  
> --max-depth 200  
> --use-sat-solver false  
> --use-procedure MApriori

The result: 
> The relevant solution:   
> 
> [1 -> 5] support: 0.4 confidence: 1.0  
> [5 -> 1] support: 0.4 confidence: 0.5714285714285715  

> Useless solutions, because 4 (Black color) is always true  
> The confidence is just the support of each variable 3=50%, 5=70%, 1=40%, 1 AND 5=40%
> 
> [4 -> 3] support: 0.5 confidence: 0.5  
> [4 -> 5] support: 0.7 confidence: 0.7  
> [4 -> 1] support: 0.4 confidence: 0.4
> [4 -> 1,5] support: 0.4 confidence: 0.4

> Useless solutions, because 4 (Back color) is always true  
> The confidence is always 100%, because of the confidence calculation:  
> Confidence(A->B) = Support(AuB)/ Support(A) = 100%/ 100% = 100% 
> 
> [3 -> 4] support: 0.5 confidence: 1.0  
> [5 -> 4] support: 0.7 confidence: 1.0  
> [1 -> 4] support: 0.4 confidence: 1.0  
> [1,5 -> 4] support: 0.4 confidence: 1.0  

> Useless solutions, because 4 (Black color) is always true  
> support(A AND B) and A=100% => support(A AND B) = support(B)  
> This solution will be considered anyway in previous calculation with survived item set = {1,5}
> 
> [1,4 -> 5] support: 0.4 confidence: 1.0  
> [4,5 -> 1] support: 0.4 confidence: 0.5714285714285715  
> [1 -> 4,5] support: 0.4 confidence: 1.0  
> [5 -> 1,4] support: 0.4 confidence: 0.5714285714285715

 






# Rules
As example, we use a configurable product like a smartphone that comes with various options such as different storage capacities, 
colors, and RAM capacities.
- 1: 64 GB storage
- 2: 128 GB storage
- 3: 256 GB storage
- 4: Black color
- 5: 8GB RAM
- 6: 16GB RAM

For the Phone example the CNF can look like (data/input_data/rule_files/Phone_example.cnf):
> p cnf 6 8  
> c Family for storage  
> 1 2 3 0  
> -1 -2 0  
> -1 -3 0  
> -2 -3 0  
> c Family for color  
> 4 0  
> c Family for accessories  
> 5 6 0  
> -5 -6 0  
> c 64GB Storage is not available with 16 GB RAM(1 -> !6)  
> -1 -6 0
