--Adam Cunningham
--Comparative language
--assignment 7
--4/28
--part c, sets
import Data.List

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




