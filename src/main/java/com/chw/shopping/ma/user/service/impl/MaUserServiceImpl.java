package com.chw.shopping.ma.user.service.impl;

import com.chw.shopping.dao.oracle.MaUserDAO;
import com.chw.shopping.ma.user.service.MaUserService;
import com.chw.shopping.ma.user.service.MaUserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MaUserServiceImpl implements MaUserService, UserDetailsService {

    private final MaUserDAO maUserDAO;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    @Override
    public Page<MaUserVO> selectList(MaUserVO vo, Pageable page) throws Exception {
        vo.setFirstPageNo(page.getPageNumber() * page.getPageSize() + 1);
        vo.setLastPageNo(vo.getFirstPageNo() + page.getPageSize() - 1);

        List<MaUserVO> resultList = maUserDAO.selectList(vo);
        int total = maUserDAO.selectCount(vo);
        return new PageImpl<>(resultList, page, total);
    }

    @Transactional(readOnly = true)
    @Override
    public int countByUserId(MaUserVO vo) {
        return maUserDAO.countByUserId(vo);
    }

    @Transactional(readOnly = true)
    @Override
    public MaUserVO selectContents(MaUserVO vo) throws Exception {
        return maUserDAO.selectContents(vo);
    }

    @Transactional
    @Override
    public void insertContents(MaUserVO vo) throws Exception {
        String encodePassword = passwordEncoder.encode(vo.getUserPw());
        vo.setUserPw(encodePassword);
        maUserDAO.insertContents(vo);
    }

    @Transactional
    @Override
    public void updateContents(MaUserVO vo) throws Exception {
        maUserDAO.updateContents(vo);
    }

    @Transactional
    @Override
    public void passwordUpdateContents(MaUserVO vo) {
        String encodePassword = passwordEncoder.encode(vo.getUserPw());
        vo.setUserPw(encodePassword);
        maUserDAO.passwordUpdateContents(vo);
    }

    @Override
    public void changePassword(MaUserVO vo) {
        String encodePassword = passwordEncoder.encode(vo.getUserPw());
        vo.setUserPw(encodePassword);
        maUserDAO.changePassword(vo);
    }

    @Transactional
    @Override
    public void deleteContents(MaUserVO vo) throws Exception {
        maUserDAO.deleteContents(vo);
    }

    @Transactional
    @Override
    public MaUserVO selectByUserId(String username) {
        return maUserDAO.selectByUserId(username);
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MaUserVO maUserVO = maUserDAO.selectByUserId(username);

        if (maUserVO == null) {
            throw new UsernameNotFoundException(username);
        }

        return User.builder()
                .username(maUserVO.getUserId())
                .password(maUserVO.getUserPw())
                .roles(maUserVO.getAuth())
                .build();
    }
}
