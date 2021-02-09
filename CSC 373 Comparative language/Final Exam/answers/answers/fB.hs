-- Template for function: fB

fB :: Num a => [a] -> a
fB = foldr1 (*)             

testB = do {
   putStrLn "\nfB:";
   print (fB [1]);
   print (fB [1..5]);
}
