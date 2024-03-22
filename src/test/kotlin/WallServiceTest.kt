import org.junit.Assert.*
import org.junit.Test

class WallServiceTest {

    @Test
    fun add_PostIdZero() {
        val post1 = Post(
            id = 1,
            ownerId = 123,
            fromId = 456,
            date = 18022024,
            text = "Пост намбер уан!"
        )

        WallService.add(post1)

        assertEquals(1, post1.id)
    }

    @Test
    fun update_Existing() {
        val post1 = Post(
            id = 1,
            ownerId = 123,
            fromId = 456,
            date = 18022024,
            text = "Пост намбер уан!"
        )

        WallService.add(post1)

        val updatedPost = post1.copy(text = "Нереально крутой пост!")
        val result = WallService.update(updatedPost)

        assertTrue(result)
    }

    @Test
    fun update_NonExisting() {
        val updatedPost = Post(
            id = 999,
            ownerId = 123,
            fromId = 456,
            date = 18022024,
            text = "Пост намбер уан!"
        )

        val result = WallService.update(updatedPost)

        assertFalse(result)
    }

    @Test(expected = PostNotFoundException::class)
    fun shouldThrow() {
        val comment = Comment(
            id = 1,
            postId = 100,
            fromId = 123,
            text = "Test comment"
        )
        WallService.createComment(comment.postId, comment)
    }

    @Test
    fun shouldAddCommentToExistingPost() {
        // Arrange
        val post = Post(
            id = 1,
            ownerId = 123,
            fromId = 456,
            date = 18022024,
            text = "Пост намбер уан!"
        )
        WallService.add(post)

        val comment = Comment(
            id = 1,
            postId = 1,
            fromId = 789,
            text = "Комментарий к посту"
        )

        val addedComment = WallService.createComment(comment.postId ?: 0, comment)

        assertEquals(comment, addedComment)
    }

}
