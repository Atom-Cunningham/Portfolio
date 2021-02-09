-- Template for function: fD

fD :: [(Int,Int)] -> Int
fD = foldr (\x -> ((+) ((fst x) + (snd x)))) 0              

testD = do {
   putStrLn "\nfD:";
   print (fD []);
   print (fD [(1,2),(3,4),(5,6)]);
}
