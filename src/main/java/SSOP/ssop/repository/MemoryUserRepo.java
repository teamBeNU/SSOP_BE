package SSOP.ssop.repository;

import SSOP.ssop.domain.User;

import java.util.*;

public class MemoryUserRepo implements UserRepo {
    private static Map<Long, User> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public User save(User user) {
        user.setUser_id(++sequence);
        store.put(user.getUser_id(), user);
        return user;
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return store.values().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(store.values());
    }

    // clearStore 메서드 추가
    public void clearStore() {
        store.clear();
    }
}
