-- Template for function: f6

f6 :: Ord a => [(a,a)] -> [a]
f6 = map (\x -> max (fst x) (snd x))               

test6 = do {
   putStrLn "\nf6:";
   print (f6 ([]::[(Int,Int)])); 
   print (f6 [(0,1),(1,2),(2,3)]); 
}
