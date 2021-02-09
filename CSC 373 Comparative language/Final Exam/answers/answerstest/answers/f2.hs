-- Template for function: f2

f2 :: [Int]
f2 = [x | x <- [1,3..]]                  

test2 = do {
   putStrLn "\nf2:";
   print (take 10 f2);
}
