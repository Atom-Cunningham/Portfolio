-- Template for function: fH

data List =
    Null |
    Cons Int List
    deriving Show

fH :: (Int -> Int) -> List -> List
fH f Null = Null                                    
fH f (Cons x k) = (Cons (f x) (fH f k))                                  

testH = do {
   putStrLn "\nfH:";
   print (fH (+1) Null);
   print (fH (+1) (Cons 1 (Cons 2 Null)));
}
