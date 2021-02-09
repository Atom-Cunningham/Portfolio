--higher order functions
--map, takes function f and list 
--applies f to all in list

--selection/filter
--takes predicate p and list
--for each a in list, if p of a is true don't filter

--fold takes seed function and list
--turns list into single element
--foldl is left associative
--foldr is right associative
--foldr1 takes the last element as a seed

--piping, uses . notation
--do g then f on a => (f . g) a

--lambda syntax (\a b -> operation)

--zip takes two lists and returns a list of pairs from a,b
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
idmatrix n = [row n i | i <- [1..n]]

--takes a function and applies [0,1..]to elements of x 
mapi1 :: (Int -> a -> b) -> [a] -> [b]
mapi1 f xs = mapi1_helper f 0 xs
 
mapi1_helper :: (Int -> a -> b) -> Int -> [a] -> [b]
mapi1_helper f n [] = []
mapi1_helper f n (x:xs) = (f n x) : (mapi1_helper f (n+1) xs)

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

--removes the duplicates from a list
--sorts the list
makeSet :: Ord a => (a -> a-> Ordering) -> [a] -> [a]
makeSet cmp xs = (Data.List.sortBy cmp . remdup) xs

--foldr, lambda
--remove duplicates
remdup :: Ord a => [a] -> [a]
remdup = foldr (\x marked -> if x `elem` marked then marked else x : marked) []

--makes a set from the list, and checks it against original list
--for equality
isSet   :: Ord a => (a -> a-> Ordering) -> [a] -> Bool
isSet cmp xs = makeSet cmp xs == xs

checkSet :: Ord a => (a -> a-> Ordering) -> [a] -> [a]
checkSet cmp xs
            | not (isSet cmp xs) = error "arguments must be sets"
            | otherwise = xs



--returns true if y is a member of xs
member :: Ord a => (a -> a-> Ordering) -> a -> [a] -> Bool
member cmp y xs= (foldl (||) False . map (\x -> x == EQ) . map (cmp y)) (checkSet cmp xs)

--returns the intersection of two sets
setIntersect :: Ord a => (a -> a-> Ordering) -> [a] -> [a] -> [a]
setIntersect cmp xs ys =  filter (\x -> member cmp x xs && member cmp x (checkSet cmp ys)) (checkSet cmp xs)

--returns the union of two sets
setUnion :: Ord a => (a -> a-> Ordering) -> [a] -> [a] -> [a]
setUnion cmp xs ys =  makeSet cmp (xs ++ ys)

--returns set a / set b
setSubtract  :: Ord a => (a -> a-> Ordering) -> [a] -> [a] -> [a]
setSubtract cmp xs ys = filter (\x -> not (member cmp x (checkSet cmp ys))) (checkSet cmp xs)

--returns true if all members of xs are in ys
setIsSubset  :: Ord a => (a -> a-> Ordering) -> [a] -> [a] -> Bool
--could have also used setSubtract here
setIsSubset cmp xs ys = (and . map (\x -> member cmp x ys)) xs

--returns the similarity between 2 sets a ^ b / a U b
setSimilarity  :: Ord a => (a -> a-> Ordering) -> [a] -> [a] -> Double
setSimilarity cmp [] [] = 0.0
setSimilarity cmp xs ys = fromIntegral (length (setIntersect cmp xs ys)) /
                          fromIntegral (length (setUnion cmp (checkSet cmp xs) (checkSet cmp ys)))


data Nat =
        Zero |
        Succ Nat
        deriving Show

--converts from an Int to a peano number
toPeano :: Int -> Nat
toPeano 0 = Zero
toPeano n = Succ (toPeano (n-1))

--converts from a peano to an int
fromPeano :: Nat -> Int
fromPeano Zero = 0
fromPeano (Succ n) = 1 + (fromPeano n)

--adds peano numbers
addPeano :: Nat -> Nat -> Nat
addPeano Zero Zero = Zero
addPeano x Zero = x
addPeano x (Succ y) = addPeano (Succ x) y

--multiply peano numbers
mulPeano :: Nat -> Nat -> Nat
mulPeano (Succ Zero) y = y
mulPeano x (Succ Zero) = x
mulPeano Zero y = Zero
mulPeano x Zero = Zero
mulPeano x (Succ y) = mulPeano (addPeano x x) y

--determines equality for peano numbers
peanoEQ :: Nat -> Nat -> Bool
peanoEQ Zero Zero = True
peanoEQ (Succ x) Zero = False
peanoEQ Zero (Succ y) = False
peanoEQ (Succ x) (Succ y) = peanoEQ x y

--determines if for peano numbers x,y x<y
peanoLT :: Nat -> Nat -> Bool
peanoLT Zero Zero = False
peanoLT x Zero = False
peanoLT Zero y = True
peanoLT (Succ x) (Succ y) = peanoLT x y




