-- Template for function: fF

fF :: [a] -> [a]
fF = m . f . z
   where 
      f = filter (odd.fst)
      z = zip [0..] 
      m = map snd

testF = do {
   putStrLn "\nfF:";
   print (fF "");
   print (fF [0..10]);
   print (fF "ABCDEFGHIJKL");
}
