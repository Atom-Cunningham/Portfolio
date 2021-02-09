-- Template for function: f8

f8 :: Int -> [[a]] -> [[a]]
f8 n = filter (\x -> (length x)>n)                   

test8 = do {
   putStrLn "\nf8:";
   print (f8 3 ([]::[[Int]])); 
   print (f8 3 [[1],[1,2],[1,2,3],[1..4],[1..5]]);
}
