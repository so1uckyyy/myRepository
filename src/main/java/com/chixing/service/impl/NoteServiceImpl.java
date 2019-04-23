package com.chixing.service.impl;

import com.chixing.dao.NoteDao;
import com.chixing.entity.Note;
import com.chixing.dao.example.NoteExample;
import com.chixing.service.NoteService;
import com.chixing.util.PageUtil;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteServiceImpl implements NoteService {
    @Autowired
    private NoteDao noteDao;
    @Override
    public List<Note> getLastTen() {
        return noteDao.selectOrderByCreateTimeDescTenRecords();
    }

    @Override
    public boolean save(Note note) {
        return noteDao.insert(note)>0;
    }

    @Override
    public List<Note> getMyList(int custId) {
        NoteExample noteExample=new NoteExample();
        noteExample.createCriteria().andCustIdEqualTo(custId);
        return noteDao.selectByExample(noteExample);
    }

    @Override
    public Note getMyLastNote(int custId) {
        return noteDao.selectOneOrderByCreateTimeDescByCustId(custId);
    }

    @Override
    public Note getById(int noteId) {
        return noteDao.selectByPrimaryKey(noteId);
    }

    @Override
    public List<Note> getAll(int pageNum) {
        PageHelper.startPage(pageNum,PageUtil.PAGE_SIZE);
        return this.noteDao.selectByExample(null);
    }

    @Override
    public int getCount() {
        return noteDao.selectCount();
    }
}
