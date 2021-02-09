apply1 :: Num a => [a->a] -> a -> a
apply1 [] n = n
apply1 xs n = apply1 (init xs) ((last xs) n)

apply2 :: Num a => [a->a] -> a -> a
apply2 xs n = foldr (\f n-> f n) n xs

test1 = apply1 [(+2)] 1 
test2 = apply1 [(+2),(*3)] 1 
test3 = apply1 [(*2),(+2),(*3)] 1
test4 = apply1 [(/1),(*2),(+2),(*3)] 5

test5 = apply2 [(+2)] 1 
test6 = apply2 [(+2),(*3)] 1 
test7 = apply2 [(*2),(+2),(*3)] 1
test8 = apply2 [(/1),(*2),(+2),(*3)] 5
