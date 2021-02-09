grade :: Float -> Float -> Char
grade f t
   | illegal f || illegal t = error "illegal score"
   | f <= 50.0 = 'E'
   | t >= 90.0 = 'A'
   | t >= 80.0 = 'B'
   | t >= 70.0 = 'C'
   | t >= 60.0 = 'D'
   | otherwise = 'E'
       where illegal x =  x > 100.0 || x < 0.0


{- 
grades takes final score and total score pairs,
and a character in question ex 'A'
and counts the number of tuples that match
the character
ex grades [(f,t)] 'A' counts the number of A's
-}
grades :: [(Float,Float)] -> Char -> Int
grades xs g = length (f xs)
           where f = filter (\x -> (grade (fst x) (snd x)) == g)


{-
stringdup creates a string of c, n long
ex stringdup '*' 3 => "***"
-}
stringDup :: Int -> Char -> String
stringDup n c = [c | x<- [1..n]]

{-row displays output histogram for some specific grade, 
row is non-recursive
-}
row :: Char -> [(Float,Float)] -> String
row grade ft = (show grade) ++ " (" ++ (show count) ++ ") " ++ (stringDup count '*') ++ "\n"
                where count = grades ft grade

{- hist takes a list of (f,t) and grades to check
and returns a representation of all the grades using row
gs is grades list, ft is pair list
-}
hist :: [Char] -> [(Float,Float)] -> String
hist gs ft = concat (map (\g -> row g ft) gs)

histogram :: [(Float,Float)] -> String
histogram ft = hist "ABCDE" ft

test = putStr (histogram [(60,90),(88,81),(50,38),(100,100),(100,100),(34,80),(92,92)])

