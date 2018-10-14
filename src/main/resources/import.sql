
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
	(SELECT count(*) FROM likes l WHERE user_id = u.id) as like_count,
	EXISTS (SELECT * FROM Follows f WHERE f.followee_id = queriedBy.id AND f.follower_id = u.id) as following,
	EXISTS (SELECT * FROM Follows f WHERE f.followee_id = u.id AND f.follower_id = queriedBy.id) as follower,
	queriedBy.id as querying_user
FROM Users u, Users queriedBy;

-- follower table
DROP TABLE IF EXISTS FollowerView;
CREATE OR REPLACE VIEW FollowerView AS
SELECT userview.*,
	follows.followee_id as followingUser,
	(SELECT username FROM users WHERE id = follows.followee_id) following_username,
	follows.created_date as followed_date
FROM userview
JOIN follows ON userview.user_id = follows.follower_id;

-- followee table
DROP TABLE IF EXISTS FollowingView;
CREATE OR REPLACE VIEW FollowingView AS
SELECT userview.*,
	follows.follower_id as followedByUser,
	(SELECT username FROM users WHERE id = follows.follower_id) followed_by_username,
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
	u.ID as user_id,
	u.USERNAME as username,
	u.profile_pic as profile_pic,
	t.created_date as tweet_timestamp,
	(SELECT count(*) FROM Likes l WHERE l.TWEET_ID = t.ID) as likeCount,
	(SELECT count(*) FROM Replies r WHERE r.PARENT_ID = t.ID) as replyCount,
	(SELECT r.PARENT_ID FROM replies r WHERE r.CHILD_ID = t.ID) as replyTo,
	EXISTS (SELECT * FROM Likes l WHERE l.user_id = queriedBy.id AND l.tweet_id = t.id) as liked,
	(SELECT array_agg(tag.tag_text) FROM tags tag WHERE tag.tweet_id = t.id) as tags,
	(SELECT array_agg(u.username) FROM users u JOIN tweets_mentions m ON u.id = m.mentions_id WHERE m.tweet_id = t.id) as mentions,
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
	(SELECT username FROM users WHERE id = likes.user_id) liked_by_username,
	likes.created_date as liked_date
FROM tweetview
INNER JOIN likes ON tweetview.id = likes.tweet_id;

--FeedView
DROP TABLE IF EXISTS FeedView;
CREATE OR REPLACE VIEW FeedView AS
SELECT
    tweetview.*,
    follows.follower_id as followed_by
FROM tweetview
JOIN follows ON tweetview.user_id = follows.followee_id;

--DESCENDANT QUERY TREE
CREATE OR REPLACE VIEW descendantOptimizedReplyTree AS
WITH RECURSIVE tweetTree AS (
	SELECT r.parent_id as ancestor, r.child_id as descendant, 1 as distance FROM replies r
	UNION
	SELECT t.ancestor as ancestor, r.child_id as descendant, t.distance + 1 as distance
	FROM replies r
	JOIN tweetTree t ON r.parent_id = t.descendant
) SELECT * FROM tweetTree;

--ANCESTOR QUERY TREE
CREATE OR REPLACE VIEW ancestorOptimizedReplyTree AS
WITH RECURSIVE tweetTree AS (
	SELECT r.parent_id as ancestor, r.child_id as descendant, 1 as distance FROM replies r
	UNION
	SELECT r.parent_id as ascendant, t.descendant as descendant, t.distance + 1 as distance
	FROM replies r
	JOIN tweetTree t ON r.child_id = t.ancestor
) SELECT * FROM tweetTree;

-- TWEET TREE
DROP TABLE IF EXISTS tweetTree;
CREATE OR REPLACE VIEW tweetTree AS
SELECT tv.*, tt.treeOf
FROM tweetview tv
JOIN
(
SELECT tree.ancestor as tweet_id, t.id as treeOf
FROM tweets t
JOIN descendantOptimizedReplyTree tree
ON tree.descendant = t.id
	UNION
SELECT t.id as tweet_id, t.id as treeOf
FROM tweets t
    UNION
SELECT tree.descendant as tweet_id, t.id as treeOf
FROM tweets t
JOIN ancestorOptimizedReplyTree tree
ON tree.ancestor = t.id
) tt
ON tv.tweet_id = tt.tweet_id;

-- TAGS VIEW QUERY
DROP TABLE IF EXISTS TweetTagView;
CREATE OR REPLACE VIEW TweetTagView AS
SELECT tweet.*, tag.tag_text as tag
FROM tweetview tweet
JOIN tags tag ON tag.tweet_id = tweet.id;

--NOTIFICATIONS
--FOLLOWS
CREATE OR REPLACE VIEW FollowNotifications AS
SELECT
	f.follower_id as sender_id,
	0 as tweet_id,
	f.created_date as ts,
	f.followee_id as reciever_id,
	'Follow' as notification_type
FROM follows f;

--LIKES
CREATE OR REPLACE VIEW LikeNotifications AS
SELECT
	l.user_id as sender_id,
	l.tweet_id as tweet_id,
	l.created_date as ts,
	t.user_id as reciever_id,
	'Like' as notification_type
FROM likes l
INNER JOIN tweets t ON l.tweet_id = t.id;

--MENTIONS
CREATE OR REPLACE VIEW MentionNotifications AS
SELECT
	t.user_id as sender_id,
	m.tweet_id as tweet_id,
	t.created_date as ts,
	m.mentions_id as reciever_id,
	'Mention' as notification_type
FROM tweets_mentions m
INNER JOIN tweets t ON t.id = m.tweet_id;

--REPLIES
CREATE OR REPLACE VIEW ReplyNotifications AS
SELECT
	ct.user_id as sender_id,
	ct.id as tweet_id,
	ct.created_date as ts,
	pt.user_id as reciever_id,
	'Reply' as notification_type
FROM replies r
INNER JOIN tweets pt ON pt.id = r.parent_id
INNER JOIN tweets ct ON ct.id = r.child_id
WHERE pt.user_id != ct.user_id;

-- UNIONED NOTIFICATIONS
CREATE OR REPLACE VIEW NotificationsUnion AS
SELECT * FROM LikeNotifications
UNION
SELECT * FROM FollowNotifications
UNION
SELECT * FROM MentionNotifications
UNION
SELECT * FROM ReplyNotifications;

-- NOTIFICATIONS
DROP TABLE IF EXISTS NotificationsView;
CREATE OR REPLACE VIEW NotificationsView AS
SELECT
    row_number() OVER () AS id,
	u.username as username,
	u.profile_pic as picture,
	n.tweet_id as tweet_id,
	n.ts as notification_timestamp,
	n.notification_type as notification_type,
	n.reciever_id as reciever_id
FROM NotificationsUnion n
INNER JOIN users u ON u.id = n.sender_id;

--END NOTIFICATIONS
DROP TABLE IF EXISTS UserPrincipalView;
CREATE OR REPLACE VIEW UserPrincipalView AS
SELECT
	u.id,
	u.username,
	u.password,
	u.profile_pic,
	COUNT(n) as notifications
FROM users u
LEFT JOIN notificationsview n ON u.id = n.reciever_id
                              AND n.notification_timestamp > u.last_notification_check
GROUP BY u.id;