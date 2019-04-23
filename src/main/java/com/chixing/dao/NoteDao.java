package com.chixing.dao;

import com.chixing.entity.Note;
import com.chixing.dao.example.NoteExample;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * NoteDao继承基类
 */
@Repository
public interface NoteDao extends MyBatisBaseDao<Note, Integer, NoteExample> {
    public List<Note> selectOrderByCreateTimeDescTenRecords();
    public Note selectOneOrderByCreateTimeDescByCustId(int custId);
    public int selectCount();
}