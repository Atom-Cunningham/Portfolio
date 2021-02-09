-- Template for function: fG

fG :: String -> String 
fG = c . f . z  
   where 
      f = foldr (\ x acc -> if (acc==[]) then [x] else x:",":acc) []
      z = (takeWhile (not.null)) . (map (take 3)) . (iterate (drop 3))
      c = concat

testG = do {
   putStrLn "\nfG:";
   print (fG "");
   print (fG "ABCDEFGHIJKL");
}
