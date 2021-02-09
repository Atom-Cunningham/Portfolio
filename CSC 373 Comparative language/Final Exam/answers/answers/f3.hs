-- Template for function: f3

f3 :: Int -> Int -> [(Int,Int)]
f3 a b = [(x,y) | x<-[1..a], y<-[1..b]]                          

test3 = do {
   putStrLn "\nf3:";
   print (f3 0 0); 
   print (f3 1 1); print (f3 3 3);
}
