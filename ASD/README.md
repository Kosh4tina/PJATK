# ASD
Algoritms and Data Structures

1. **Fibonacci sequence.**  
Write an algorithm for computing the nth term of the Fibonacci sequence.  
Input: natural number n  
Output: nth term of the Fibonacci sequence.  
  
2. **Sequence of natural numbers.**  
Consider a random sequence of C integers. Give the length and the sum of the elements the longest possible monotone  
and coherent sequence C. If the answer is ambiguous, indicate the first such sequence starting from left side.  
  
3. **Labirint**  
The task is to find a way out of the maze. We create the maze by randomly generating it  
a zero-one matrix of 10x10 size and two points: S-start, K-end.  
**Sample matrix:**  
1 1 1 0 1 0 1 0 1 1  
1 0 0 1 1 0 0 1 0 1  
1 1 **S** 1 0 0 1 0 0 0  
0 0 1 0 0 1 0 0 1 0  
1 0 1 0 0 1 0 0 0 1  
0 1 1 1 1 1 0 0 0 0  
0 0 0 1 0 1 0 1 0 1  
1 1 1 0 0 1 1 0 1 1  
0 1 0 0 0 0 1 1 1 1  
0 0 0 0 1 1 0 1 **F** 1  
The solution should be to answer whether there is a path from point S to point K, assuming that:  
• We can only move up, down, right, left  
• 1 means way, 0 means no way  
The output should include whether there is such a path or not, and if it does, list the coordinates of the points  
expensive. If there is more than one road, enter the shorter one and write its coordinates. Reply to  
the example above should be:  
**Start (3.3), End (10.9)**  
There is a way: (3.3), (4.3), (5.3), (6.3), (6.4), (6.5), (6.6), (7.6) , (8.6), (8.7), (9.7), (9.8), (9.9), (10.9)  
