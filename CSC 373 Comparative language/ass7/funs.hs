--Adam Cunningham
--Comparative language
--assignment 7
--4/28
--part b, functions

import Data.List
--fold
--max' computes the largest element of the list using fold
max' :: (Ord a) => [a] -> a
max' [] = error "empty list"
max' xs = foldl max (head xs) xs

--map
--mul' creates a list of all members of a passed in list
--multiplied by x
mul' :: (Num a) => [a] -> a -> [a]
mul' xs x = map (*x) xs

--function composition, where
--sumc below, uses function composition to 
--compute the sum of the cubes of all the
--numbers divisible by 7 in a list xs of integers
sumc :: [Int] -> Int
sumc = foldr (+) 0. map cube . filter by7
    where cube x = x*x*x           --returns int
          by7  x = (x `mod` 7) == 0--returns bool

--lambda, filter
--siever n xs which returns the 
--elements of xs that are not multiples of n
siever :: Int -> [Int] -> [Int]
siever 0 xs = [] --nothing is multiple of zero, including 0
siever n xs = filter (\x -> x `mod` n /= 0) xs

--list comprehension
--returns a list of n 0s, except vth element, which is 1
row :: Int -> Int -> [Int]
row n v = [if i == v then 1 else 0 | i <- [1..n]]

--nested list comprehension
--idmatrix returns an identity matrix of size n
idmatrix :: Int -> [[Int]]
idmatrix n = [row n i |   i <- [1..n]]

--takes a function and applies [0,1..]to elements of x 
mapi1 :: (Int -> a -> b) -> [a] -> [b]
mapi1 f xs = mapi1_helper f 0 xs
 
mapi1_helper :: (Int -> a -> b) -> Int -> [a] -> [b]
mapi1_helper f n [] = []
mapi1_helper f n (x:xs) = (f n x) : (mapi1_helper f (n+1) xs)

--same as mapi1, but with map and zip
mapi2 :: (Int -> a -> b) -> [a] -> [b]
mapi2 f xs = map (\x -> f (fst x) (snd x)) (zip [0,1..] xs)

--map, lambda
--listify takes every element of a list and turns it into a list
listify :: [a] -> [[a]]
listify f = map (\x -> [x]) f

--foldr
--flatten flattens a list of lists
flatten :: [[a]] -> [a]
flatten = foldr (++) []

--composition, foldr, map
--note that x:xs when xs is a [char], x is also a [char]
--so you need to take the head
--and map doesnt work on [char], so you have to listify to get a [[char]]
--vowelrep replaces all vowels with .
vowelrep :: [Char] -> [Char]
vowelrep xs = (flatten . map vowToPeriod . listify) xs
            where vowToPeriod x
                      | foldr (||) False (map (== head x) "aeiouy") = "."
                      | otherwise = x

--composition, lambda
--removes all vowels from a string
unvowel :: [Char] -> [Char]
unvowel xs = (flatten . filter (\x -> x /= ".") . listify . vowelrep) xs


letters =[('a', ".-"), ('b', "-..."), ('c', "-.-."),
          ('d', "-.."), ('e', "."),('f', "..-."), 
          ('g', "--."), ('h', "...."), ('i', ".."), 
          ('j', ".---"),('k', "-.-"), ('l', ".-.."), 
          ('m', "--"), ('n', "-."), ('o', "---"),
          ('p', ".--."), ('q', "--.-"), ('r', ".-."), 
          ('s', "..."), ('t', "-"),('u', "..-"), 
          ('v', "...-"), ('w', ".--"), ('x', "-..-"), 
          ('y', "-.--"),('z', "--.."), ('1', ".----"), 
          ('2', "..---"), ('3', "...--"), ('4', "....-"),
          ('5', "....."), ('6', "-...."), ('7', "--..."), 
          ('8', "---.."), ('9', "----."),('0', "-----"), 
          (' ', "_")]

--strings, filter, map, lambda, composition
--string2morse converts a string into morse code
string2morse :: [Char] -> [Char]
string2morse = concat . replace
     where
       lookUp x xs = (snd . head . filter (\y -> x == fst y)) xs
       concat      = (\xs -> if xs == "" then "" else init xs) 
                     . foldr (++) "" . map (\x -> x ++ " ")
       replace     = map (\x -> lookUp x letters)



