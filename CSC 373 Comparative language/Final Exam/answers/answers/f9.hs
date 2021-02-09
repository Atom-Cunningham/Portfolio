-- Template for function: f9

f9 :: Ord a => [(a,a)] -> [(a,a)]
f9 = filter (\x -> (fst x) > (snd x))             

test9 = do {
   putStrLn "\nf9:";
   print (f9 [(0,1),(1,2),(2,3),(5,1)]);
}
