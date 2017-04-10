package tigerislandserver.gameplay;

import java.util.ArrayList;

public class RoundRobinSchedule extends ScheduleType {
    private int numOfParticipants;

    public RoundRobinSchedule(Scheduler parent, int numOfParticipants){
        super(parent);
        this.numOfParticipants = numOfParticipants;
    }

    @Override
    public ArrayList<Matchup> getMatchups(int round) {
        // returns 0 indexed matchup of participants in round-robin tournament
        int[] workingArray = getRoundArray(round);

        ArrayList<Matchup> newMatchups = calculateMatchups(workingArray, round);
        return newMatchups;
    }

    private int[] getRoundArray(int round) {
        // if round < 1, returns array for first round
        round = round < 1 ? 1 : round;
        boolean odd = ((numOfParticipants % 2) == 1);

        int finalArrayLength = odd ? (numOfParticipants + 1) : numOfParticipants;

        int[] baseArray = new int[finalArrayLength];
        for(int i = 0; i < finalArrayLength; ++i){
            baseArray[i] = (i + 1);
        }

        int[] workingArray = new int[finalArrayLength - 1];
        for(int i = 1; i < finalArrayLength; ++i){
            workingArray[i - 1] = baseArray[i];
        }

        workingArray = rotate(workingArray, (round - 1));

        int[] finalArray = new int[finalArrayLength];
        finalArray[0] = 1;

        for(int idx = 0; idx < workingArray.length; ++idx){
            finalArray[idx + 1] = workingArray[idx];
        }
        return finalArray;
    }

    private int[] rotate(int[] array, int rotations){
        int length = array.length;
        int[] temp = new int[length];
        int positionMod = Math.floorMod((Math.floorMod((length - rotations), length) + length), length);

        for(int i = 0; i < length; ++i){
            int position = ((i + positionMod) % length);
            temp[i] = array[position];
        }

        return temp;
    }

    private ArrayList<Matchup> calculateMatchups (int[] array, int round){
        int arrayLength = array.length;
        boolean even = ((arrayLength % 2) == 0);
        if(!even){
            // this shouldn't happen...throw error?
        }

        ArrayList<Matchup> newMatchupArray = new ArrayList<Matchup>();
        for(int i = 0; i < (arrayLength / 2); ++i){
            int player1Index = array[i] - 1;
            int player2Index = array[arrayLength - (i + 1)] - 1;
            if(player1Index >= numOfParticipants || player2Index >= numOfParticipants) {
                continue;
            }
            else {
                Matchup tempMatchup = new Matchup(player1Index, player2Index);
                newMatchupArray.add(tempMatchup);
            }
        }

        return newMatchupArray;
    }

    @Override
    public int getTotalRounds() {
        if(numOfParticipants == 0)
            return 0;
        return (getTotalMatches() / getMatchesPerRound());
    }

    @Override
    public int getTotalMatches(){
        return (numOfParticipants * (numOfParticipants - 1)) / 2;
    }

    private int getMatchesPerRound(){
        return (numOfParticipants % 2) == 0 ?
                (numOfParticipants / 2) : ((numOfParticipants - 1) / 2);
    }
}
