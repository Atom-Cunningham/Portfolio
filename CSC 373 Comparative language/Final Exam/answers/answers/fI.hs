-- Template for function: fI

data List =               
    Null |                
    Cons Int List         
    deriving Show         

fI :: (Int -> Bool) -> List -> List
fI f Null = Null                                                          
fI f (Cons x k) = if (f x) then (Cons x (fI f k))
                  else (fI f k)                                                        

testI = do {
   putStrLn "\nfI:";
   print (fI (>0) Null);
   print (fI (>1) (Cons 1 (Cons 2 Null)));
}
