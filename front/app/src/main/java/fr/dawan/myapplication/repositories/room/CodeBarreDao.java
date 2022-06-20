package fr.dawan.myapplication.repositories.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import fr.dawan.myapplication.entities.room.CodeBarre;

@Dao
public interface CodeBarreDao {

    @Insert
    void insert(CodeBarre codeBarre);

    @Query("SELECT * FROM t_codebarre")
    List<CodeBarre> getAll();
}
