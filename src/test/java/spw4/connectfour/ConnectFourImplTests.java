package spw4.connectfour;

import java.security.InvalidParameterException;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ConnectFourImplTests {

    static Stream<Arguments> ctor_withValidPlayer_ReturnsConnectFourImplClass() {
        return Stream.of(
                Arguments.of(Player.red), Arguments.of(Player.yellow)
        );
    }

    static Stream<Arguments> getPlayerOnTurn_ReturnsCurrentPlayer() {
        return Stream.of(
                Arguments.of(Player.red), Arguments.of(Player.yellow)
        );
    }

    static Stream<Arguments> isGameOver_ReturnsFalse() {
        return Stream.of(
                Arguments.of(Player.red), Arguments.of(Player.yellow)
        );
    }    static Stream<Arguments> drop_withInvalidCoords_ThrowsException() {
        return Stream.of(
                Arguments.of(-10),
                Arguments.of(-1),
                Arguments.of(7),
                Arguments.of(38)
        );
    }

    static Stream<Arguments> drop_withValidCoords_PlacesCoin() {
        return Stream.of(
                Arguments.of(0),
                Arguments.of(2),
                Arguments.of(6)
        );
    }

    static Stream<Arguments> drop_withValidCoords_ButAlreadyFullyOccupiedRow_ThrowsException() {        return Stream.of(
                Arguments.of(0),
                Arguments.of(2),
                Arguments.of(6)
        );
    }

    static Stream<Arguments> toString_PrintsCorrectBoard() {
        return Stream.of(
                Arguments.of(0)
        );
    }

    @Test
    void ctor_withNull_ThrowsException() {
        assertThatThrownBy(() -> new ConnectFourImpl(null))
                .isInstanceOf(InvalidParameterException.class);
    }

    @Test
    void ctor_withWrongPlayer_ThrowsException() {
        assertThatThrownBy(() -> new ConnectFourImpl(Player.none))
                .isInstanceOf(InvalidParameterException.class);
    }

    @ParameterizedTest
    @MethodSource
    void ctor_withValidPlayer_ReturnsConnectFourImplClass(Player player) {
        ConnectFour cf = new ConnectFourImpl(player);

        assertThat(cf).isInstanceOf(ConnectFourImpl.class);
    }

    @ParameterizedTest
    @MethodSource
    void getPlayerOnTurn_ReturnsCurrentPlayer(Player player) {
        ConnectFour cf = new ConnectFourImpl(player);

        assertThat(cf.getPlayerOnTurn()).isEqualTo(player);
    }

    @ParameterizedTest
    @MethodSource
    void isGameOver_ReturnsFalse(Player player) {
        ConnectFour cf = new ConnectFourImpl(player);

        assertThat(cf.isGameOver()).isEqualTo(false);
    }

    @ParameterizedTest
    @MethodSource
    void drop_withInvalidCoords_ThrowsException(int col) {
        ConnectFour cf = new ConnectFourImpl(Player.red);

        assertThatThrownBy(() -> cf.drop(col)).isInstanceOf(InvalidParameterException.class);
    }

    @ParameterizedTest
    @MethodSource
    void drop_withValidCoords_PlacesCoin(int col) {
        ConnectFour cf = new ConnectFourImpl(Player.red);        cf.drop(col);

        assertThat(cf.getPlayerAt(5, col)).isNotEqualTo(Player.none);
    }

    @ParameterizedTest
    @MethodSource
    void drop_withValidCoords_ButAlreadyFullyOccupiedRow_ThrowsException(int col) {
        ConnectFour cf = new ConnectFourImpl(Player.red);

        cf.drop(col);
        cf.drop(col);
        cf.drop(col);
        cf.drop(col);
        cf.drop(col);
        cf.drop(col);

        assertThatThrownBy(() -> cf.drop(col)).isInstanceOf(RuntimeException.class);
    }

    @ParameterizedTest
    @MethodSource
    void toString_PrintsCorrectBoard(int col) {
        ConnectFour cf = new ConnectFourImpl(Player.red);

        cf.drop(col);

        String board =
                "| .  .  .  .  .  .  . |\n" +
                        "| .  .  .  .  .  .  . |\n" +
                        "| .  .  .  .  .  .  . |\n" +
                        "| .  .  .  .  .  .  . |\n" +
                        "| .  .  .  .  .  .  . |\n" +
                        "| R  .  .  .  .  .  . |\n";

        assertThat(cf.toString()).isEqualTo(board);
    }

    @Test
    void hasFourConnected_HorizontalWin_GameIsOver() {
        ConnectFour cf = new ConnectFourImpl(Player.red);

        cf.drop(1);
        cf.drop(1);
        cf.drop(2);
        cf.drop(2);
        cf.drop(3);
        cf.drop(3);
        cf.drop(4);

        assertThat(cf.isGameOver()).isTrue();
        assertThat(cf.getWinner()).isEqualTo(Player.red);
    }

    @Test
    void hasFourConnected_VerticalWin_GameIsOver() {
        ConnectFour cf = new ConnectFourImpl(Player.red);

        cf.drop(1);
        cf.drop(2);
        cf.drop(1);
        cf.drop(2);
        cf.drop(1);
        cf.drop(2);
        cf.drop(1);

        assertThat(cf.isGameOver()).isTrue();
        assertThat(cf.getWinner()).isEqualTo(Player.red);
    }

    @Test
    void hasFourConnected_DiagonalDownRightWin_GameIsOver() {
        ConnectFour cf = new ConnectFourImpl(Player.red);

        cf.drop(1);
        cf.drop(2);
        cf.drop(2);
        cf.drop(3);
        cf.drop(3);
        cf.drop(4);
        cf.drop(3);
        cf.drop(4);
        cf.drop(4);
        cf.drop(1);
        cf.drop(4);

        assertThat(cf.isGameOver()).isTrue();
        assertThat(cf.getWinner()).isEqualTo(Player.red);
    }

    @Test
    void hasFourConnected_DiagonalDownLeftWin_GameIsOver() {
        ConnectFour cf = new ConnectFourImpl(Player.red);

        cf.drop(4);
        cf.drop(3);
        cf.drop(3);
        cf.drop(2);
        cf.drop(2);
        cf.drop(1);
        cf.drop(2);
        cf.drop(1);
        cf.drop(1);
        cf.drop(4);
        cf.drop(1);

        assertThat(cf.isGameOver()).isTrue();
        assertThat(cf.getWinner()).isEqualTo(Player.red);
    }

    @Test
    void hasFourConnected_YellowWins_GameIsOver() {
        ConnectFour cf = new ConnectFourImpl(Player.yellow);

        cf.drop(1);
        cf.drop(5);
        cf.drop(2);
        cf.drop(5);
        cf.drop(3);
        cf.drop(5);
        cf.drop(4);

        assertThat(cf.isGameOver()).isTrue();
        assertThat(cf.getWinner()).isEqualTo(Player.yellow);
    }

    @Test
    void hasFourConnected_NoWin_GameContinues() {
        ConnectFour cf = new ConnectFourImpl(Player.red);

        cf.drop(1);
        cf.drop(2);
        cf.drop(3);
        cf.drop(4);
        cf.drop(5);
        cf.drop(6);

        assertThat(cf.isGameOver()).isFalse();
        assertThat(cf.getWinner()).isEqualTo(Player.none);
    }

    @Test    void hasFourConnected_RightEdge_NoBoundaryViolation() {
        ConnectFour cf = new ConnectFourImpl(Player.red);

        cf.drop(6);  // rightmost column (0-based)
        cf.drop(5);  // second from right column (0-based)
        cf.drop(6);  // rightmost column again

        assertThat(cf.isGameOver()).isFalse();
    }

    @Test
    void hasFourConnected_LeftEdge_NoBoundaryViolation() {
        ConnectFour cf = new ConnectFourImpl(Player.red);

        cf.drop(1);
        cf.drop(2);
        cf.drop(1);

        assertThat(cf.isGameOver()).isFalse();
    }

    @Test
    void hasFourConnected_BottomEdge_NoBoundaryViolation() {
        ConnectFour cf = new ConnectFourImpl(Player.red);

        cf.drop(1);
        cf.drop(2);
        cf.drop(3);

        assertThat(cf.isGameOver()).isFalse();
    }

    @Test
    void reset_withValidPlayerRed_ResetsGameState() {
        ConnectFour cf = new ConnectFourImpl(Player.yellow);        cf.drop(0);  // column 0 (0-based)
        cf.drop(1);  // column 1 (0-based)
        cf.drop(2);  // column 2 (0-based)

        cf.reset(Player.red);

        assertThat(cf.getPlayerOnTurn()).isEqualTo(Player.red);
        assertThat(cf.isGameOver()).isFalse();
        assertThat(cf.getWinner()).isEqualTo(Player.none);

        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                assertThat(cf.getPlayerAt(row, col)).isEqualTo(Player.none);
            }
        }
    }

    @Test
    void reset_withValidPlayerYellow_ResetsGameState() {        ConnectFour cf = new ConnectFourImpl(Player.red);

        cf.drop(0);  // column 0 (0-based)
        cf.drop(1);  // column 1 (0-based)
        cf.drop(2);  // column 2 (0-based)

        cf.reset(Player.yellow);

        assertThat(cf.getPlayerOnTurn()).isEqualTo(Player.yellow);
        assertThat(cf.isGameOver()).isFalse();
        assertThat(cf.getWinner()).isEqualTo(Player.none);

        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                assertThat(cf.getPlayerAt(row, col)).isEqualTo(Player.none);
            }
        }
    }

    @Test
    void reset_withNull_ThrowsException() {
        ConnectFour cf = new ConnectFourImpl(Player.red);

        assertThatThrownBy(() -> cf.reset(null))
                .isInstanceOf(InvalidParameterException.class);
    }

    @Test
    void reset_withPlayerNone_ThrowsException() {
        ConnectFour cf = new ConnectFourImpl(Player.red);

        assertThatThrownBy(() -> cf.reset(Player.none))
                .isInstanceOf(InvalidParameterException.class);
    }

    @Test
    void reset_afterGameOver_ResetsGameState() {
        ConnectFour cf = new ConnectFourImpl(Player.red);

        cf.drop(1);
        cf.drop(1);
        cf.drop(2);
        cf.drop(2);
        cf.drop(3);
        cf.drop(3);
        cf.drop(4);

        assertThat(cf.isGameOver()).isTrue();
        assertThat(cf.getWinner()).isEqualTo(Player.red);

        cf.reset(Player.yellow);

        assertThat(cf.getPlayerOnTurn()).isEqualTo(Player.yellow);
        assertThat(cf.isGameOver()).isFalse();
        assertThat(cf.getWinner()).isEqualTo(Player.none);        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                assertThat(cf.getPlayerAt(row, col)).isEqualTo(Player.none);
            }
        }
    }

    @Test
    void reset_duringActiveGame_ClearsBoard() {
        ConnectFour cf = new ConnectFourImpl(Player.red);        cf.drop(0);  // column 0 (0-based)
        cf.drop(1);  // column 1 (0-based)
        cf.drop(2);  // column 2 (0-based)

        assertThat(cf.getPlayerAt(5, 0)).isEqualTo(Player.red);    // bottom row, column 0
        assertThat(cf.getPlayerAt(5, 1)).isEqualTo(Player.yellow); // bottom row, column 1  
        assertThat(cf.getPlayerAt(5, 2)).isEqualTo(Player.red);    // bottom row, column 2

        cf.reset(Player.red);

        assertThat(cf.getPlayerOnTurn()).isEqualTo(Player.red);
        assertThat(cf.isGameOver()).isFalse();
        assertThat(cf.getWinner()).isEqualTo(Player.none);

        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                assertThat(cf.getPlayerAt(row, col)).isEqualTo(Player.none);
            }
        }
    }    @Test
    void getPlayerAt_withInvalidRow_ThrowsException() {
        ConnectFour cf = new ConnectFourImpl(Player.red);

        assertThatThrownBy(() -> cf.getPlayerAt(-1, 0))
                .isInstanceOf(InvalidParameterException.class);
        assertThatThrownBy(() -> cf.getPlayerAt(6, 0))
                .isInstanceOf(InvalidParameterException.class);
    }

    @Test
    void getPlayerAt_withInvalidCol_ThrowsException() {
        ConnectFour cf = new ConnectFourImpl(Player.red);

        assertThatThrownBy(() -> cf.getPlayerAt(0, -1))
                .isInstanceOf(InvalidParameterException.class);
        assertThatThrownBy(() -> cf.getPlayerAt(0, 7))
                .isInstanceOf(InvalidParameterException.class);
    }
}
