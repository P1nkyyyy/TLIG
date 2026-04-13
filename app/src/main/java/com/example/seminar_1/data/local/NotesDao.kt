package com.example.seminar_1.data.local

import androidx.room.Dao
import androidx.room.Query
import com.example.seminar_1.features.messages.data.model.NoteModel
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {
    @Query("SELECT * FROM notes WHERE id = :noteId AND messageId = :messageId")
    fun getNote(noteId: Int, messageId: Int): Flow<NoteModel?>
}