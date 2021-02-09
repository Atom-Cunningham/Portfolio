-- Template for function: f7

f7 :: Ord a => a -> [a] -> [a]
f7 n = filter (>n)        

test7 = do {
   putStrLn "\nf7:";
   print (f7 10 [1..10]); 
   print (f7 5 [1..10]);
}
