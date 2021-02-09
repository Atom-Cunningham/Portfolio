-- Template for function: f4

f4 :: Num a => a -> [a] -> [a]
f4 n = map (+ n)                 

test4 = do {
   putStrLn "\nf4:";
   print (f4 0 [1..10]); 
   print (f4 5 [1..10]);
}
