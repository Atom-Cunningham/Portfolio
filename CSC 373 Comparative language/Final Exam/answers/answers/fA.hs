-- Template for function: fA

fA :: Num a => [a] -> a
fA = foldr (+) 0             

testA = do {
   putStrLn "\nfA:";
   print (fA []);
   print (fA [1..5]);
}
