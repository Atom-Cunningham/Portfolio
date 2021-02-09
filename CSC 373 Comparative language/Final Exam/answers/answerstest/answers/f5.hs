-- Template for function: f5

f5 :: [[a]] -> [Int]
f5 = map (\x -> (length x))                         

test5 = do {
   putStrLn "\nf5:";
   print (f5 []); 
   print (f5 [[1],[1,2],[1,2,3]]);
}
