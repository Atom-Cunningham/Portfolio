-- Template for function: f1

f1 :: Int -> [Int]
f1 n = [x | x <- [1], i <- [1..n]]       

test1 = do {
    putStrLn "\nf1:";
    print (f1 0); 
    print (f1 5); 
}
