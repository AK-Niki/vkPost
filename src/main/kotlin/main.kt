fun main() {
    val post1 = Post(
        id = 1,
        ownerId = 123,
        fromId = 456,
        date = 18022024,
        text = "Пост намбер уан!",
        attachments = listOf(
            PhotoAttachment(Photo(1, 123, "photo130_url", "photo604_url")),
            VideoAttachment(Video(2, 456, "Video Title", 60)),
            AudioAttachment(Audio(3, 789, "Audio Title", 120)),
            DocumentAttachment(Document(4, 101112, "Document Title", 1024)),
            VoiceMessageAttachment(VoiceMessage(5, 131415, 30))
        )
    )

    val post2 = Post(
        id = 2,
        ownerId = 789,
        fromId = 101112,
        date = 20022024,
        text = "Это второй пост!",
        attachments = listOf(
            PhotoAttachment(Photo(6, 789, "photo130_url_2", "photo604_url_2")),
            VideoAttachment(Video(7, 101112, "Video Title 2", 45))
        )
    )

    WallService.add(post1)
    WallService.add(post2)

    println("Посты:")
    for (post in WallService.posts) {
        println(post)
    }

    println("\nОбновление поста:")
    val updatedPost = post1.copy(text = "Нереально крутой пост!")
    WallService.update(updatedPost)
    println("Обновленный пост: $updatedPost")

        val photoAttachment = PhotoAttachment(Photo(1, 123, "photo130", "photo604"))
        val videoAttachment = VideoAttachment(Video(2, 456, "video title", 60))
        val audioAttachment = AudioAttachment(Audio(3, 789, "audio title", 120))
        val documentAttachment = DocumentAttachment(Document(4, 101112, "document title", 1024))
        val voiceMessageAttachment = VoiceMessageAttachment(VoiceMessage(5, 131415, 30))

        println(photoAttachment.type)
        println(videoAttachment.type)
        println(audioAttachment.type)
        println(documentAttachment.type)
        println(voiceMessageAttachment.type)

    // Создаем комментарий
    val comment = Comment(
        id = 1,
        postId = 1,
        fromId = 789,
        text = "Какой же крутой пост!"
    )

    // Добавляем комментарий к посту
    try {
        WallService.createComment(comment.postId ?: 0, comment)
        println("Комментарий успешно добавлен")
    } catch (e: PostNotFoundException) {
        println(e.message)
    }
    }


data class Comments(
    val count: Int = 0, // количество комментариев
    val canPost: Boolean = false,
)

data class Likes(
    val count: Int = 0, // количество пользователей, которым понравилась запись
    val userLikes: Boolean = false,
)

data class Reposts(
    val count: Int = 0, // количество пользователей, скопировавших запись
)

data class Views(
    val count: Int = 0 // количество просмотров записи
)

data class Post(
    val id: Int?, // идентификатор записи
    val ownerId: Int?, // идентификатор владельца стены, на которой размещена запись
    val fromId: Int?, // идентификатор автора записи
    val date: Int?, // дата публикации записи
    val text: String?, // текст записи
    val comments: Comments? = Comments(), // информация о комментариях к записи
    val likes: Likes? = Likes(), // информация о лайках к записи
    val reposts: Reposts? = Reposts(), // информация о репостах записи
    val views: Views? = Views(), // информация о просмотрах записи
    val canDelete: Boolean = false, // информация о том, может ли текущий пользователь удалить запись
    val canEdit: Boolean = false, // информация о том, может ли текущий пользователь редактировать запись
    val attachments: List<Attachment> = emptyList() // список вложений
)

sealed class Attachment(val type: String)

// фотографии
data class PhotoAttachment(val photo: Photo) : Attachment("photo")

// видео
data class VideoAttachment(val video: Video) : Attachment("video")

// аудио
data class AudioAttachment(val audio: Audio) : Attachment("audio")

// документы
data class DocumentAttachment(val document: Document) : Attachment("document")

// голосовые сообщения
data class VoiceMessageAttachment(val voiceMessage: VoiceMessage) : Attachment("voiceMessage")

data class Photo(
    val id: Int,
    val ownerId: Int,
    val photo130: String,
    val photo604: String
)

data class Video(
    val id: Int,
    val ownerId: Int,
    val title: String,
    val duration: Int
)

data class Audio(
    val id: Int,
    val ownerId: Int,
    val title: String,
    val duration: Int
)

data class Document(
    val id: Int,
    val ownerId: Int,
    val title: String,
    val size: Int
)

data class VoiceMessage(
    val id: Int,
    val ownerId: Int,
    val duration: Int
)

data class Comment(
    val id: Int,
    val postId: Int,
    val text: String,
    val fromId: Int? = null
)

class PostNotFoundException(message: String) : Exception(message)

object WallService {
    val posts = ArrayList<Post>()
    private var comments = emptyArray<Comment>()

    fun createComment(postId: Int, comment: Comment): Comment {
        var post: Post? = null
        for (p in posts) {
            if (p.id == postId) {
                post = p
                break
            }
        }
        if (post == null) {
            throw PostNotFoundException("Пост с id $postId не найден")
        }

        val newComment = comment.copy(id = comments.size + 1, postId = postId)
        comments += newComment
        return newComment
    }

    private var currentId = 0

    fun add(post: Post): Post {
        currentId++
        val newPost = post.copy(id = currentId)
        posts.add(newPost)
        return newPost
    }

    fun update(post: Post): Boolean {
        for (i in posts.indices) {
            if (posts[i].id == post.id) {
                posts[i] = post
                return true
            }
        }
        return false
    }
}