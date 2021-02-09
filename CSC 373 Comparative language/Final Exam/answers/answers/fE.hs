-- Template for function: fE

fE :: Num a => [a] -> [a]
fE = m . a  
   where 
      m = map (*2)
      a = map (+1)

testE = do {
   putStrLn "\nfE:";
   print (fE []);
   print (fE [1,2,3]);
}
