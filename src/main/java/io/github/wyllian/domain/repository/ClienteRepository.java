package io.github.wyllian.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import io.github.wyllian.domain.entity.Cliente;


public interface ClienteRepository extends JpaRepository<Cliente, Integer> {


    List<Cliente> findByNomeLike(String string);
    List<Cliente> findByNomeLikeOrId(String nome, Integer id);
    boolean existsByNome(String nome);
    void deleteByNome(String nome);

    @Query(value = "delete from Cliente c where c.nome = :nome")
    @Modifying
    void deletarPorNome(@Param("nome") String nome);

    // Uso de consulta HQL
    @Query(value = "select c from Cliente c where c.nome like :nome")
    List<Cliente> buscarPeloNome(@Param("nome") String nome);

    // Uso de consulta SQL NATIVE
    @Query(value = "select *from cliente c where c.nome like '%:nome%'", nativeQuery = true)
    List<Cliente> buscarPeloNomeQueryNativa(@Param("nome") String nome);

    // JPQL JÁ FAZ O RELACIONAMENTO
    @Query(value = "select c from Cliente c left join fetch c.pedidos where c.id = :id")
    Cliente findClienteFetchPedidos(@Param("id") Integer id);





//    private static String INSERT = "insert into cliente (nome) values (?)";
//    private static String SELECT_ALL = "select *from cliente";
//    private static String UPDATE = "update cliente set nome= ? where id = ?";
//    private static String DELETE = "delete cliente where id = ?";
//
//    @Autowired
//    private JdbcTemplate jdbcTemplate;

    // @Autowired
    // private EntityManager entityManager;

//     @Transactional
//     public Cliente salvar(Cliente cliente) {
// //        jdbcTemplate.update(
// //                INSERT,
// //                new Object[]{cliente.getNome()}
// //        );
//         entityManager.persist(cliente);
//         return cliente;
//     }

//     @Transactional
//     public List<Cliente> list() {
//         return entityManager.createQuery("from Cliente", Cliente.class).getResultList();

//         //return jdbcTemplate.query("select *from cliente", obterClienteMapper());
//     }

//     @Transactional
//     public Cliente update(Cliente c) {
// //        jdbcTemplate.update(UPDATE, new Object[]{c.getNome(), c.getId()});
//         entityManager.merge(c);
//         return c;
//     }

    // @Transactional
    // public void deletar(Cliente c) {
    //     if(!entityManager.contains(c))
    //         c = entityManager.merge(c);
    //     entityManager.remove(c);
    //     //deletar(c.getId());
    // }

//     @Transactional
//     public void deletar(int id) {
//         Cliente cliente = entityManager.find(Cliente.class, id);
//         deletar(cliente);
//         //jdbcTemplate.update(DELETE, new Object[]{id});
//     }

//     @Transactional(readOnly = true)
//     public List<Cliente> buscarPorNome(String nome) {

//         String jpql = " select c from cliente c where c.nome like :nome ";
//         TypedQuery<Cliente> query =  entityManager.createQuery(jpql, Cliente.class);
//         query.setParameter("nome", "%"+ nome+"%");
//         return query.getResultList();

// //        return jdbcTemplate.query(
// //                SELECT_ALL.concat(" where nome like ?"),
// //                obterClienteMapper(),
// //                new Object[]{"%" + nome + "%"}
// //        );
//     }

//     private RowMapper<Cliente> obterClienteMapper() {
//         return new RowMapper<Cliente>() {
//             @Override
//             public Cliente mapRow(ResultSet rs, int rowNum) throws SQLException {
//                 return new Cliente(rs.getInt("id"), rs.getString("nome"));
//             }
//         };
//     }
}
