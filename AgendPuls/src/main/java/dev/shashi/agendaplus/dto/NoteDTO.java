package dev.shashi.agendaplus.dto;

public class NoteDTO {
    private int noteId;
    private String content;

    // Constructors
    public NoteDTO() {}

    public NoteDTO(int noteId, String content) {
        this.noteId = noteId;
        this.content = content;

    }

    // Getters and Setters
    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
