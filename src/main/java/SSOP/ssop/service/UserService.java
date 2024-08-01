package SSOP.ssop.service;

import SSOP.ssop.domain.User;
import SSOP.ssop.dto.UserDto;
import SSOP.ssop.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 회원가입
    public void saveUser(User user) {
        User u = userRepository.save(new User(
                user.getUser_name(),
                user.getUser_birth().toString(),
                user.getUser_phone(),
                user.getPassword(),
                user.getEmail()));
    }

    // 유저 모든 정보 출력
    public List<UserDto> getUsers() {
        return userRepository.findAll().stream()
                .map(UserDto::new)
                .collect(Collectors.toList());
    }

    // 유저 정보 업데이트 -> 일단 pw만
    public void updateUser(UserDto userDto) {
        User user = userRepository.findById(userDto.getUser_id())
                .orElseThrow(IllegalAccessError::new);
        user.updatePassword(userDto.getPassword());
        userRepository.save(user);
    }

    // userId로 유저 삭제
    public void deleteUser(long userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(IllegalArgumentException::new);

        userRepository.delete(user);
    }
}
