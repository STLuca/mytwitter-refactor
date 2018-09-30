
-- New UserView
DROP TABLE IF EXISTS UserView;
CREATE OR REPLACE VIEW UserView AS
SELECT
    u.id as id,
	u.ID as user_id,
	u.USERNAME as username,
	u.profile_pic as profile_pic,
	(SELECT count(*) FROM Tweets t where t.USER_ID = u.id) as tweet_count,
	(SELECT count(*) FROM Follows f where f.FOLLOWER_ID = u.id) as following_count,
	(SELECT count(*) FROM Follows f where f.FOLLOWEE_ID = u.id) as follower_count,
	EXISTS (SELECT * FROM Follows f WHERE f.followee_id = queriedBy.id AND f.follower_id = u.id) as following,
	EXISTS (SELECT * FROM Follows f WHERE f.followee_id = u.id AND f.follower_id = queriedBy.id) as follower,
	queriedBy.id as querying_user
FROM Users u, Users queriedBy;

-- follower table
DROP TABLE IF EXISTS FollowerView;
CREATE OR REPLACE VIEW FollowerView AS
SELECT userview.*,
	follows.followee_id as followingUser,
	follows.created_date as followed_date
FROM userview
JOIN follows ON userview.user_id = follows.follower_id;

-- followee table
DROP TABLE IF EXISTS FollowingView;
CREATE OR REPLACE VIEW FollowingView AS
SELECT userview.*,
	follows.follower_id as followedByUser,
	follows.created_date as followed_date
FROM userview
JOIN follows ON userview.user_id = follows.followee_id;

-- New TweetView
DROP TABLE IF EXISTS TweetView;
CREATE OR REPLACE VIEW TweetView AS
SELECT
    t.id as id,
	t.ID as tweet_id,
	t.MESSAGE as message,
	u.ID as userID,
	u.USERNAME as username,
	u.profile_pic as profile_pic,
	t.created_date as tweet_timestamp,
	(SELECT count(*) FROM Likes l WHERE l.TWEET_ID = t.ID) as likeCount,
	(SELECT count(*) FROM Replies r WHERE r.PARENT_ID = t.ID) as replyCount,
	(SELECT r.PARENT_ID FROM replies r WHERE r.CHILD_ID = t.ID LIMIT 1) as replyTo,
	EXISTS (SELECT * FROM Likes l WHERE l.user_id = queriedBy.id AND l.tweet_id = t.id) as liked,
	(SELECT array_agg(tag.tag_text) FROM tags tag WHERE tag.tweet_id = t.id) as tags,
	queriedBy.id as querying_user
FROM Tweets t
INNER JOIN Users u ON t.USER_ID = u.ID, Users queriedBy;

-- TweetLikesView
DROP TABLE IF EXISTS TweetLikesView;
CREATE OR REPLACE VIEW TweetLikesView AS
SELECT
	userview.*,
	likes.tweet_id AS likedTweet,
	likes.created_date AS liked_date
FROM userview
INNER JOIN likes ON userview.id = likes.user_id;

-- UsersLikesView
DROP TABLE IF EXISTS UsersLikesView;
CREATE OR REPLACE VIEW UsersLikesView AS
SELECT
	tweetview.*,
	likes.user_id as likedBy,
	likes.created_date as liked_date
FROM tweetview
INNER JOIN likes ON tweetview.id = likes.tweet_id;

DROP TABLE IF EXISTS FeedView;
CREATE OR REPLACE VIEW FeedView AS
SELECT
    tweetview.*,
    follows.follower_id as followed_by
FROM tweetview
JOIN follows ON tweetview.userid = follows.followee_id;

INSERT INTO users Values
    (2, '$2a$04$ulrrvKPPOk.FvKNPhffJWe0TjTbMYT6e0k0egroxaEvf6bL6dYNM2', 'https://amp.businessinsider.com/images/5899ffcf6e09a897008b5c04-750-750.jpg', 'bob'),
    (3, 'bbb', '', 'susan'),
    (4, 'ccc', '', 'larry');

INSERT INTO tweets Values
	(4, 0, 'tweet 1 by bob', 2),
	(5, 1, 'tweet 2 by bob', 2),
	(6, 2, 'tweet 3 by susan', 3),
	(7, 3, 'tweet 4 by susan', 3),
	(8, 4, 'tweet 5 by larry', 4),
	(9, 5, 'tweet 6 by larry', 4),
	(22, 1, 'tweet 2 by bob', 2),
	(23, 1, 'tweet 2 by bob', 2),
	(24, 1, 'tweet 2 by bob', 2),
	(25, 1, 'tweet 2 by bob', 2),
	(26, 1, 'tweet 2 by bob', 2),
	(27, 1, 'tweet 2 by bob', 2);


INSERT INTO likes Values
    (10, 6, 6, 2),
    (11, 7, 9, 2),
    (12, 8, 4, 3),
    (13, 9, 5, 3),
    (14, 10, 4, 4),
    (15, 11, 6, 4);

INSERT INTO follows Values
	(16, 11, 2, 2),
	(17, 12, 2, 3),
	(18, 13, 2, 4),
	(19, 14, 3, 2),
	(20, 15, 3, 4);

INSERT INTO tags Values
    (4, 'first'),
    (4, 'new'),
    (5, 'second');