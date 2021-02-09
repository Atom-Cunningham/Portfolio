-- Template for function: fC

fC :: a -> [a] -> [a]
fC x = foldr (\y ys -> (y:x:ys)) []                   

testC = do {
   putStrLn "\nfC:";
   print (fC 1 []);
   print (fC 1 [2,2,2,2]);
}
